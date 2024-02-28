package dev.ng.ng_dev_zone_back.services;

import dev.ng.ng_dev_zone_back.entities.App;
import dev.ng.ng_dev_zone_back.exceptions.ResourceNotFoundException;
import dev.ng.ng_dev_zone_back.repositories.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppService {

    @Autowired
    private AppRepository appRepository;

    public List<App> getAllApps() {
        return appRepository.findAll();
    }

    public App getAppById(Long id) {
        return appRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("App not found with id: " + id));
    }

    public App createApp(App app) {
        return appRepository.save(app);
    }

    public App updateApp(Long id, App appDetails) {
        App app = appRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("App not found with id: " + id));
        app.setName(appDetails.getName());
        app.setDescription(appDetails.getDescription());
        app.setUrl(appDetails.getUrl());
        app.setLogo(appDetails.getLogo());
        return appRepository.save(app);
    }

    public void deleteApp(Long id) {
        App app = appRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("App not found with id: " + id));
        appRepository.delete(app);
    }
}
