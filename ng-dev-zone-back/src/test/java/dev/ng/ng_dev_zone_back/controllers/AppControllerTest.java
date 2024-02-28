package dev.ng.ng_dev_zone_back.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ng.ng_dev_zone_back.entities.App;
import dev.ng.ng_dev_zone_back.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppService appService;
    @InjectMocks
    private AppController appController;


    @Test
    void getAllApps() throws Exception {
        App app = new App(1L, "App1", "Description1", "http://app1.com", "logo1.png");
        App app2 = new App(2L, "App2", "Description2", "http://app2.com", "logo2.png");

        List<App> apps = List.of(app, app2);
        when(appService.getAllApps()).thenReturn(apps);

        mockMvc.perform(get("/api/apps"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("App1")))
                .andExpect(jsonPath("$[0].description", is("Description1")))
                .andExpect(jsonPath("$[0].url", is("http://app1.com")))
                .andExpect(jsonPath("$[0].logo", is("logo1.png")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("App2")))
                .andExpect(jsonPath("$[1].description", is("Description2")))
                .andExpect(jsonPath("$[1].url", is("http://app2.com")))
                .andExpect(jsonPath("$[1].logo", is("logo2.png")));
    }

    @Test
    void getAppById() throws Exception {
        App app = new App(1L, "App1", "Description1", "http://app1.com", "logo1.png");
        when(appService.getAppById(1L)).thenReturn(app);

        mockMvc.perform(get("/api/apps/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("App1")))
                .andExpect(jsonPath("$.description", is("Description1")))
                .andExpect(jsonPath("$.url", is("http://app1.com")))
                .andExpect(jsonPath("$.logo", is("logo1.png")));
    }

    @Test
    public void createApp() throws Exception {
        App mockApp = new App(1L, "App1", "Description1", "http://app1.com", "logo1.png");

        when(appService.createApp(any(App.class))).thenReturn(mockApp);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/apps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"example\":\"data\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("App1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", is("Description1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url", is("http://app1.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.logo", is("logo1.png")));
    }

    @Test
    public void updateApp() throws Exception {
        // Create a mock App object with updated details
        App updatedApp = new App(1L, "UpdatedApp", "UpdatedDescription", "http://updatedapp.com", "updated_logo.png");
        when(appService.updateApp(eq(1L), any(App.class))).thenReturn(updatedApp);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/apps/{appId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedApp)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("UpdatedApp")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", is("UpdatedDescription")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url", is("http://updatedapp.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.logo", is("updated_logo.png")));
    }


    @Test
    void deleteApp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/apps/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}