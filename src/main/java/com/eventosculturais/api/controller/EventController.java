package com.eventosculturais.api.controller;

import com.eventosculturais.api.domain.coupon.Coupon;
import com.eventosculturais.api.domain.eventos.Event;
import com.eventosculturais.api.domain.eventos.EventDetailsDTO;
import com.eventosculturais.api.domain.eventos.EventRequestDTO;
import com.eventosculturais.api.domain.eventos.EventResponseDTO;
import com.eventosculturais.api.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired  // O Spring injeta automaticamente a instância de ServiceA
    private EventService eventService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Event> create(@RequestParam("title") String title,
                                        @RequestParam("description") String description,
                                        @RequestParam("date") Long date,
                                        @RequestParam("city") String city,
                                        @RequestParam("state") String state,
                                        @RequestParam("remote") Boolean remote,
                                        @RequestParam("eventUrl") String eventUrl,
                                        @RequestParam(value = "image",required = false) MultipartFile image){
        EventRequestDTO eventRequestDTO = new EventRequestDTO(title, description, date, city, state, remote, eventUrl, image);
        Event newEvent = this.eventService.createEvent(eventRequestDTO);
        return ResponseEntity.ok(newEvent);

    }

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getEvents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        List<EventResponseDTO> allEvents = this.eventService.getUpcomingEvents(page,size);
        return ResponseEntity.ok(allEvents);

    }

    @GetMapping("/filter")
    public ResponseEntity<List<EventResponseDTO>> filterEvents(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               @RequestParam(required = false) String title,
                                                               @RequestParam(required = false) String city,
                                                               @RequestParam(required = false) String state,
                                                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
                                                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate)
    {
        List<EventResponseDTO> filterEvents = eventService.getFilteredEvents(page,size,title,city,state,startDate,endDate);
        return ResponseEntity.ok(filterEvents);


    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDetailsDTO> getEventDetails(@PathVariable UUID eventId){
        EventDetailsDTO eventDetailsDTO = eventService.getEventByID(eventId);
        return ResponseEntity.ok(eventDetailsDTO);

    }


}
