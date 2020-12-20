package com.unololtd.nazmul.springrest.service.impl;

import com.unololtd.nazmul.springrest.common.PageAttr;
import com.unololtd.nazmul.springrest.entity.User;
import com.unololtd.nazmul.springrest.repository.UserRepository;
import com.unololtd.nazmul.springrest.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*Returns User with the specified id
     * @param id     ID for the User to retrieve.
     * @return       The request User if found
     * */
    @Override
    public Optional<User> getById(Long id) {
        if (this.exists(id)) {
            return this.userRepository.findById(id);
        }
        return Optional.empty();
    }

    /*Return all User in the database
     * @return   All User in the database.
     * */
    @Override
    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    /*Return all users page by page from database
     * @param pageNumber     pageNumber for specified page
     * @return UserPage      User page with specified page number
     * */
    @Override
    public Page<User> getAll(Pageable pageable) {
//        return this.userRepository.findAll(PageAttr.getPageRequest(page));
        return this.userRepository.findAll(pageable);
    }

    /*Return User page with searching parameter
     * @param query      the content that is searching into database
     * @param page       the userPage number for pagination
     * @return           user page that found with searching parameter from our database with
     *                   pagination
     *                   */
    @Override
    public Page<User> searchUser(String query, int page) {
        return this.userRepository.findDistinctByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query, PageAttr.getPageRequest(page));
    }

    /*Save the specified User in the database
     * @param user       the user entity to save into the database
     * @return           the saved user
     * */
    @Override
    public User save(User user) {

        //Update Version and set username if first
        if (user.getId() == null) {
            user.setVersion(1);
            user.setUsername(user.getPhone());
        } else {
            user.setVersion(user.getVersion() + 1);
        }

        return this.userRepository.save(user);
    }

    /*Return true if user with specified id exist into the database
     * @param id     the user id that was looking for
     * @return       true if specified id exist into the database
     * */
    @Override
    public boolean exists(long id) {
        return this.userRepository.existsById(id);
    }

    /*Delete the user with the specified id
     * @param id     the id of the user to delete
     * @return       true if the operation was successful
     * */
    @Override
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }

    /*Count number of Users in the database
     * @return   number Users in the database
     * */
    @Override
    public long count() {
        return this.userRepository.count();
    }
}
