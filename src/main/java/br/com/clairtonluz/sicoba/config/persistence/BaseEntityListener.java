package br.com.clairtonluz.sicoba.config.persistence;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created by clairtonluz<clairton.c.l@gmail.com> on 23/04/16.
 */
@Configurable
public class BaseEntityListener {

    @PrePersist
    public void onCreate(Object target) {
        if (target instanceof BaseEntity) {
            ((BaseEntity) target).setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            ((BaseEntity) target).setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        }
    }

    @PreUpdate
    public void onUpdate(Object target) {
        if (target instanceof BaseEntity) {
            ((BaseEntity) target).setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        }
    }
}
