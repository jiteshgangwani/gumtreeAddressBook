package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class Main {

    public static void main(String[] args) {

        List<PersonDetail> personDetailList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

        try{
            File inputF = new File("resource/AddressBook");
            InputStream inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

          personDetailList =  br.lines().map(str ->{
                String[] arr = str.split(",");

                PersonDetail personDetail = new PersonDetail(arr[0],arr[1].trim(), LocalDate.parse(arr[2].trim(),formatter));

                return personDetail;
            }).collect(Collectors.toList());
           br.close();
        } catch ( IOException e) {
            e.printStackTrace();

        }


    System.out.println("Number of males are " +    personDetailList.stream().filter(personDetail -> personDetail.getGender().equals("Male")).count());


    Optional<PersonDetail> maxAgePerson =  personDetailList.stream().min(Comparator.comparing(PersonDetail::getDateOfBirth));



        System.out.println("Oldest person is  " + maxAgePerson.get().getName() );



    Optional<PersonDetail> bill =   personDetailList.stream().filter(personDetail -> personDetail.getName().equals("Bill McKnight")).findAny();
    Optional<PersonDetail> paul =   personDetailList.stream().filter(personDetail -> personDetail.getName().equals("Paul Robinson")).findAny();

    LocalDate billDateOfBirth = bill.get().getDateOfBirth();
    LocalDate paulDateOfBirth = paul.get().getDateOfBirth();

    System.out.println(" Date difference between Paul and Bill is " + DAYS.between(billDateOfBirth,paulDateOfBirth));


    }




}
