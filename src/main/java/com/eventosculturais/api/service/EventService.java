package com.eventosculturais.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.eventosculturais.api.domain.coupon.Coupon;
import com.eventosculturais.api.domain.eventos.Event;
import com.eventosculturais.api.domain.eventos.EventDetailsDTO;
import com.eventosculturais.api.domain.eventos.EventRequestDTO;
import com.eventosculturais.api.domain.eventos.EventResponseDTO;
import com.eventosculturais.api.repository.CouponRepository;
import com.eventosculturais.api.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private AmazonS3 S3Client;
    @Autowired
    private AmazonS3 createS3Instance;

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CouponService couponService;


    public Event createEvent(EventRequestDTO data) {
        String imgUrl = null;

        if(data.image() != null) {
            imgUrl = this.uploadImg(data.image());
        }
        Event newEvent = new Event();
        newEvent.setTitle(data.title());
        newEvent.setDescription(data.description());
        newEvent.setEventUrl(data.eventUrl());
        newEvent.setDate(new Date(data.date()));
        newEvent.setImgURL(imgUrl);
        newEvent.setRemote(data.remote());

        eventRepository.save(newEvent);

        if(!data.remote()){
            this.addressService.createAddress(data,newEvent);
        }


        return newEvent;
    }

    private String uploadImg(MultipartFile multipartFile) {
        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        try{
            File file = this.convertMultipartToFile(multipartFile);
            S3Client.putObject(bucketName,fileName,file);
            file.delete();
            return S3Client.getUrl(bucketName,fileName).toString();

        } catch (Exception e){}
        System.out.println("erro ao subir arquivo");
        return " ";
    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }

    public List<EventResponseDTO> getUpcomingEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = this.eventRepository.findUpcomingEvents(new Date(),pageable);
        return eventsPage.map(event -> new EventResponseDTO(event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getDate(),
                        event.getAddress()!= null ? event.getAddress().getCity() :"",
                        event.getAddress()!= null ? event.getAddress().getUf() :"",
                        event.getRemote(),
                        event.getEventUrl(),
                        event.getImgURL())
                )
                .stream().toList();

    }


    public List<EventResponseDTO> getFilteredEvents(int page, int size, String title, String city, String state, Date startDate, Date endDate) {
        title = (title != null) ? title : "";
        city = (city != null) ? city : "";
        state = (state != null) ? state : "";
        startDate = (startDate != null) ? startDate : new Date(0);
        endDate = (endDate != null) ? endDate : getFutureDate(10);

        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = this.eventRepository.findFilterEvents(title,city,state,startDate,endDate,pageable);
        return eventsPage.map(event -> new EventResponseDTO(event.getId(),
                                                            event.getTitle(),
                                                            event.getDescription(),
                                                            event.getDate(),
                                                            event.getAddress()!= null ? event.getAddress().getCity() :"",
                                                            event.getAddress()!= null ? event.getAddress().getUf() :"",
                                                            event.getRemote(),
                                                            event.getEventUrl(),
                                                            event.getImgURL())
                )
                .stream().toList();
    }


    private Date getFutureDate(int years){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

   public EventDetailsDTO getEventByID(UUID eventId){
        Event event = this.eventRepository.findById(eventId).
                orElseThrow(()-> new IllegalArgumentException("Evento nao encontrado"));

        List<Coupon> coupons = couponService.consultarCoupons(eventId,new Date());

        List<EventDetailsDTO.CouponDTO> couponDTOS = coupons.stream()
                .map(coupon -> new EventDetailsDTO.CouponDTO(
                        coupon.getCode(),
                        coupon.getDiscount(),
                        coupon.getValid()))
                .collect(Collectors.toList());

        return new EventDetailsDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getAddress() != null ? event.getAddress().getCity() : "",
                event.getAddress() != null ? event.getAddress().getUf() : "",
                event.getRemote(),
                event.getEventUrl(),
                event.getImgURL(),
                couponDTOS);
    }

}
