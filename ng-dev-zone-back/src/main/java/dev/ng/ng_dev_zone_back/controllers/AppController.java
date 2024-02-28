package dev.ng.ng_dev_zone_back.controllers;

import dev.ng.ng_dev_zone_back.entities.App;
import dev.ng.ng_dev_zone_back.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apps")
public class AppController {

    @Autowired
    private AppService appService;

    @GetMapping
    public List<App> getAllApps() {
        return appService.getAllApps();
    }


    // Get
    @GetMapping("/{appId}")
    public ResponseEntity<App> getAppById(@PathVariable Long appId) {
        App app = appService.getAppById(appId);
        return ResponseEntity.ok().body(app);
    }

    // Post
    @PostMapping
    public ResponseEntity<App> createApp(@RequestBody App app) {
        App createdApp = appService.createApp(app);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdApp);
    }


    // Put
    @PutMapping("/{appId}")
    public ResponseEntity<App> updateApp(@PathVariable Long appId, @RequestBody App appDetails) {
        App updatedApp = appService.updateApp(appId, appDetails);
        return ResponseEntity.ok().body(updatedApp);
    }


    // Delete
    @DeleteMapping("/{appId}")
    public ResponseEntity<?> deleteApp(@PathVariable Long appId) {
        appService.deleteApp(appId);
        return ResponseEntity.ok().build();
    }
}
