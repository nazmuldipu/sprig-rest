package com.unololtd.nazmul.springrest.assembler;

import com.unololtd.nazmul.springrest.controller.EmployeeController;
import com.unololtd.nazmul.springrest.entity.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {
    @Override
    public EntityModel<User> toModel(User user) {
        return EntityModel.of(user, //
                linkTo(methodOn(EmployeeController.class).one(user.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("users"));
    }
}
