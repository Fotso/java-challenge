package jp.co.axa.apidemo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * UserInfo
 * Represent a UserInfo entity.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_INFO")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    @Getter
    @Setter
    @Column(name = "USERNAME", unique = true)
    private String userName;

    @Getter
    @Setter
    @Column(name = "PASSWORD")
    private String password;

    @Getter
    @Setter
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Getter
    @Setter
    @Column(name = "AUTHORITIES")
    private String authorities;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserInfo)) {
            return false;
        }
        return this.userName.equals(((UserInfo) o).userName);
    }

    @Override
    public int hashCode() {
        return this.userName.hashCode();
    }
}
