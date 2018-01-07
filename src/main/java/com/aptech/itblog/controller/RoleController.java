package com.aptech.itblog.controller;

import com.aptech.itblog.collection.Role;
import com.aptech.itblog.model.CommonResponseBody;
import com.aptech.itblog.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

import static com.aptech.itblog.common.CollectionLink.*;

@RestController
@RequestMapping(value = API)
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping(value = ROLES)
    public ResponseEntity<?> getRoleList(@RequestParam(required = false, defaultValue = "0") Integer page,
                                             @RequestParam(required = false, defaultValue = "25") Integer size) {
        // Create pageable
        Pageable pageable = new PageRequest(page, size);
        Page<Role> rolePage = roleRepository.findAll(pageable);
        // Init a headers and add Content-Range
        HttpHeaders headers = new HttpHeaders() {
            {
                add("Access-Control-Expose-Headers", "Content-Range");
                add("Content-Range", String.valueOf(rolePage.getTotalElements()));
            }
        };

        return new ResponseEntity<>(
                new CommonResponseBody("OK", 200,
                        new LinkedHashMap() {
                            {
                                put("data", rolePage.getContent());
                            }
                        }), headers, HttpStatus.OK);

    }

    @PostMapping(value = ROLES)
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        Role checkRole = roleRepository.findByAuthority(role.getAuthority());
        if (checkRole == null) {
            return new ResponseEntity<>(
                    new CommonResponseBody("Conflict", 409,
                            new LinkedHashMap() {
                                {
                                    put("message", "This role has already been created.");
                                    put("data", "");
                                }
                            }), HttpStatus.CONFLICT);
        }
        // Save role to DB
        roleRepository.save(role);
        return new ResponseEntity<>(
                new CommonResponseBody("OK", 200,
                        new LinkedHashMap() {
                            {
                                put("message", "Successfully created a new role");
                                put("data", role);
                            }
                        }), HttpStatus.OK);

    }

    @PutMapping(value = ROLES_ID)
    public ResponseEntity<?> getRole(@PathVariable(value = "id") String roleId,
                                     @RequestBody Role role) {
        Role currentRole = roleRepository.findOne(roleId);
        // Set update properties
        currentRole.setAuthority(role.getAuthority());
        // Save to DB
        roleRepository.save(currentRole);

        return new ResponseEntity<>(
                new CommonResponseBody("OK", 200,
                        new LinkedHashMap() {
                            {
                                put("message", "Successfully updated specified role");
                                put("data", role);
                            }
                        }), HttpStatus.OK);

    }

    @GetMapping(value = ROLES_ID)
    public ResponseEntity<?> updateRole(@PathVariable(value = "id") String roleId) {
        Role role = roleRepository.findOne(roleId);
        return new ResponseEntity<>(
                new CommonResponseBody("OK", 200,
                        new LinkedHashMap() {
                            {
                                put("message", "Successfully fetched specified role");
                                put("data", role);
                            }
                        }), HttpStatus.OK);

    }

    @DeleteMapping(value = ROLES_ID)
    public ResponseEntity<?> deleteRole(@PathVariable(value = "id") String roleId) {
        roleRepository.delete(roleId);
        return new ResponseEntity<>(
                new CommonResponseBody("OK", 200,
                        new LinkedHashMap() {
                            {
                                put("message", "Successfully deleted specified role");
                                put("data", roleId);
                            }
                        }), HttpStatus.OK);

    }

}