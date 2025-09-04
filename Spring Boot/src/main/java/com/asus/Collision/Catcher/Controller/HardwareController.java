package com.asus.Collision.Catcher.Controller;

import com.asus.Collision.Catcher.Entity.Hardware;
import com.asus.Collision.Catcher.Entity.MapData;
import com.asus.Collision.Catcher.Entity.Speed;
import com.asus.Collision.Catcher.Entity.User;
import com.asus.Collision.Catcher.Firebase.FirebaseSender;
import com.asus.Collision.Catcher.Service.HardwareService;
import com.asus.Collision.Catcher.Service.SmsService;
import com.asus.Collision.Catcher.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/hardware")
public class HardwareController {

    @Autowired
    private HardwareService hardwareService;

    @Autowired
    private UserService userService;

    @Autowired
    private SmsService smsService;


    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public ResponseEntity<?> addHardware(@RequestBody Hardware hardware)
    {
        String user = hardware.getUserName();
        User byName = userService.findByName(user);
        Hardware hardware1 = hardwareService.saveHardware(hardware);
        if(hardware1!=null)
        {
            byName.setHardware(hardware1);
            userService.saveUser(byName);
        }
        return new ResponseEntity<>(byName.getHardware(),HttpStatus.CREATED);
    }

    //@PutMapping
    /*public ResponseEntity<?> updateHardware(@RequestBody Hardware hardware)
    {
        String user = hardware.getUserName();
        User byName = userService.findByName(user);
        Hardware hardwareById = hardwareService.getHardwareById(hardware.getSerialNo());
        if(hardwareById!=null)
        {
            hardwareById.setSerialNo((hardwareById.getSerialNo()!=null && !hardwareById.getSerialNo().isEmpty())?hardware.getSerialNo():hardwareById.getSerialNo());
            Hardware hardware1 = hardwareService.saveHardware(hardwareById);
            return new ResponseEntity<>(hardware1,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(hardware,HttpStatus.NOT_ACCEPTABLE);
    }*/


    @PostMapping("/alert")
    public ResponseEntity<?> alertGenerated(@RequestBody String body) throws Exception {
        //FirebaseSender firebaseSender = new FirebaseSender("src/main/java/com/asus/Collision/Catcher/Firebase/service-account.json");
        //firebaseSender.sendMessage("Token: eVmVFfZpRfCsxAHCFkbbxL:APA91bEOSMj1ibH_TyzdN93Ip8nx5-n9LGWvGDA2UolALPMnhvMuISXBkSzlrFCkuWzZ8oTYSmRV9EEkk2grJJ8dffj4MKiONPL5c84aTQ-5axlAAAfThco","Collision Alert","Collision has been detected");
        // smsService.sendSms("Accident Detected","7398081491");
        return new ResponseEntity<>("ALERT GENERATED",HttpStatus.OK);
    }

    @GetMapping
    public String checkHardware(){
        return "Hello Hardware";
    }

    @GetMapping("/disable-motor")
    public ResponseEntity<?> disableMotor()
    {
        String espIp = "http://192.168.109.104";
        String url = espIp + "/disable-motor";
        try
        {
            String response = restTemplate.getForObject(url,String.class);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (RestClientException e) {
            return new ResponseEntity<>("Error Connecting to ESP32"+e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/enable-motor")
    public ResponseEntity<?> enableMotor()
    {
        String espIp = "http://192.168.109.104";
        String url = espIp + "/enable-motor";
        try
        {
            String response = restTemplate.getForObject(url,String.class);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (RestClientException e) {
            return new ResponseEntity<>("Error Connecting to ESP32"+e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-gps-data")
    public ResponseEntity<?> getGpsData()
    {
        String espIp = "http://192.168.137.125";
        String url = espIp + "/sendGps";
        try
        {
            String response = restTemplate.getForObject(url,String.class);
            ObjectMapper mapper = new ObjectMapper();
            MapData mapData = mapper.readValue(response, MapData.class);
                return new ResponseEntity<>(mapData,HttpStatus.OK);
        } catch (RestClientException e) {
            return new ResponseEntity<>("Error Connecting to ESP32"+e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/get-speed-data")
    public ResponseEntity<?> getSpeedData()
    {
        String espIp = "http://192.168.131.104";
        String url = espIp + "/send-speed-data";
        try
        {
            String response = restTemplate.getForObject(url, String.class);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (RestClientException e) {
            return new ResponseEntity<>("Error Connecting to ESP32"+e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/send-gps-data")
    public ResponseEntity<?> sendGpsToClient(@RequestBody Map<String, Object> payload)
    {
        //send Map to client;
        return new ResponseEntity<>("Data Sended",HttpStatus.OK);
    }

    @PostMapping("/send-speed-data")
    public ResponseEntity<?> sendSpeedData(@RequestBody Map<String,Object> payload)
    {
        //send Map to client;
        return new ResponseEntity<>("Data Sended",HttpStatus.OK);
    }

}
