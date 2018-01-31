package com.spark.spark

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

interface PersonRepository: JpaRepository<Person, Long>

@RestController @RequestMapping(value = "/person") @EnableWebMvc
class PersonResource(val personRepo: PersonRepository){

    @GetMapping(value = "/")
    fun getAll() = personRepo.findAll()

    @GetMapping(value = "/{id}")
    fun getOne(@PathVariable id: Long) = personRepo.findById(id)

    @PostMapping(value = "/")
    fun create(@RequestBody person: Person): Person{
        val prsn = Person(
                firstname = person.firstname,
                lastname = person.lastname,
                email = person.email,
                phonenumber = person.email,
                birth = person.birth
        )
        personRepo.save(prsn)
        return prsn
    }

    @DeleteMapping(value = "/{id}")
    fun delete(@PathVariable id: Long) = personRepo.deleteById(id)

    @PutMapping(value = "/{id}")
    fun update(@PathVariable id: Long, @RequestBody person: Person): Person{
        val perUpdate: Person = personRepo.findById(id).orElseThrow { Exception("server error") }
        perUpdate.firstname = person.firstname
        perUpdate.lastname = person.lastname
        perUpdate.email = person.email
        perUpdate.phonenumber = person.phonenumber
        perUpdate.birth = person.birth
        return personRepo.save(perUpdate)

    }
}

@Entity
class Person(@Id @GeneratedValue(strategy = GenerationType.AUTO)
        val Id: Long = 0, var firstname: String = "", var lastname: String = "", var email: String = "", var phonenumber: String = "", var birth: String = "")