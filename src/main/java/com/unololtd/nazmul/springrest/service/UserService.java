package com.unololtd.nazmul.springrest.service;

import com.unololtd.nazmul.springrest.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    /*Returns User with the specified id
     * @param id     ID for the User to retrieve.
     * @return       The request User if found
     * */
    Optional<User> getById(Long id);

    /*Find by username or phone*/
    Optional<User> findByUsernameOrPhone(String username);

    /*Return all User in the database
     * @return   All User in the database.
     * */
    List<User> getAll();

    /*Return all users page by page from database
     * @param pageNumber     pageNumber for specified page
     * @return UserPage      User page with specified page number
     * */
    Page<User> getAll(Pageable pageable);

    /*Return User page with searching parameter
     * @param query      the content that is searching into database
     * @param page       the userPage number for pagination
     * @return           user page that found with searching parameter from our database with
     *                   pagination
     *                   */
    Page<User> searchUser(String query, int page);

    /*Save the specified User in the database
     * @param user       the user entity to save into the database
     * @return           the saved user
     * */
    User save(User user);

    /*Return true if user with specified id exist into the database
     * @param id     the user id that was looking for
     * @return       true if specified id exist into the database
     * */
    boolean exists(long id);

    /*Delete the user with the specified id
     * @param id     the id of the user to delete
     * @return      void
     * */
    void deleteById(Long id);

    /*Count number of Users in the database
     * @return   number Users in the database
     * */
    long count();
}
