package com.unololtd.nazmul.springrest.controller;

import com.unololtd.nazmul.springrest.assembler.UserModelAssembler;
import com.unololtd.nazmul.springrest.common.MyUtil;
import com.unololtd.nazmul.springrest.entity.User;
import com.unololtd.nazmul.springrest.exception.UserNotFoundException;
import com.unololtd.nazmul.springrest.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;
    private final UserModelAssembler assembler;

    public UserController(UserService service, UserModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping("")
    ResponseEntity<EntityModel<User>> newUser(@RequestBody User user) {

        user.setUsername(user.getPhone());
        User newUser = service.save(user);

        return ResponseEntity //
                .created(linkTo(methodOn(UserController.class).one(newUser.getId())).toUri()) //
                .body(assembler.toModel(newUser));
    }

    @PutMapping("/{id}")
    ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        User updateUser = service.getById(id) //
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    return service.save(user);
                }) //
                .orElseGet(() -> {
                    newUser.setId(id);
                    newUser.setUsername(newUser.getPhone());
                    return service.save(newUser);
                });

        EntityModel<User> entityModel = assembler.toModel(updateUser);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/list")
    public CollectionModel<EntityModel<User>> all() {
        List<EntityModel<User>> users = service.getAll().stream() //
                .map(assembler::toModel) //
                .sorted(Comparator.comparingLong(f -> f.getContent().getId()))
                .collect(Collectors.toList());

        return CollectionModel.of(users, //
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @GetMapping(value = "")
    public ResponseEntity<PagedModel<User>> page(Pageable pageable, PagedResourcesAssembler passembler) {
        Page<User> users = this.service.getAll(pageable);

        for (final User user : users.getContent()) {
            Link selfLink = linkTo(methodOn(UserController.class).one(user.getId())).withSelfRel();
            user.add(selfLink);
        }

        PagedModel<User> ur = passembler.toModel(users, linkTo(UserController.class).withSelfRel());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Link", MyUtil.createLinkHeader(ur));
        return new ResponseEntity(ur, responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public EntityModel<User> one(@PathVariable Long id) {

        User user = service.getById(id) //
                .orElseThrow(() -> new UserNotFoundException(id));

        return assembler.toModel(user);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
