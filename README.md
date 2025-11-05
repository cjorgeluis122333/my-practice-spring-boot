# Sping boot application
I will use this app for a lot of project and I do not think deprecate this project.
## What do you find
* [Create image]
* [Spring Data JPA]
* [Spring Security]
* [Validation]
* [Spring Web]
* [Docker]
* [Di]
* [Paging]

## Sample of controller
```java

@RequestMapping("/api/admin")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;


    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(adminService.findAllUsers());
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> adminRegisterUser(@Valid @RequestBody UserEntity user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return RequestValidation.validation(bindingResult);

        user.setAdmin(false);
        return ResponseEntity.ok(adminService.insertUser(user));
    }

    @PostMapping("/createAdmin")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> createAdmin(@Valid @RequestBody UserEntity user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return RequestValidation.validation(bindingResult);
        user.setAdmin(true);
        return ResponseEntity.ok(adminService.insertUser(user));
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> adminUpdateUser(@Valid @RequestBody UserEntity user, @PathVariable long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return RequestValidation.validation(bindingResult);
        UserEntity userEntity;
        try {
            userEntity = adminService.updateUser(id, user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Boolean> deleteUser(@RequestParam long id) {
        if (adminService.deleteUser(id))
            return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

}
```
## Sample of service

```java

@Service
public class AdminServiceImpl implements AdminService {
    final UserRepository userRepository;
    final AuthenticationService authentication;
    final BCryptPasswordEncoder encoder;

    public AdminServiceImpl(UserRepository userRepository, AuthenticationService service, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.authentication = service;
        this.encoder = encoder;
    }


    @Override
    @Transactional
    public UserEntity updateUser(long idToUpdate, UserEntity user) throws UserNotFoundException {
        if (userRepository.findById(idToUpdate).isEmpty()) {
            throw new UserNotFoundException();
        }
        user.setId(idToUpdate);
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean deleteUser(long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty())
            return false;
        else {
            userRepository.delete(userEntity.orElseThrow());
            return true;
        }
    }

    @Override
    @Transactional
    public UserEntity insertUser(UserEntity user) {
        return authentication.register(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> findAllUsers() {
        return userRepository.findAllUsersEntity().orElseThrow();
    }
}
```

## Sample of repository
```java

@Repository
public interface StudentRepository extends CrudRepository<StudentEntity, Long> {

    @Query("select s from  StudentEntity  s where s.first_name like %:name%")
    Optional<List<StudentEntity>> findStudentEntitiesByFirst_name(@Param("name") String firstName);

    @Query("select s from  StudentEntity  s where s.dni_student = :dni")
    Optional<StudentEntity> findStudentEntitiesByDni_student(@Param("dni") String dni);

}
```
