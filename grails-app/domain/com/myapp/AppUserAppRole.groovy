package com.myapp

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class AppUserAppRole implements Serializable {

	private static final long serialVersionUID = 1

	AppUser appUser
	AppRole appRole

	@Override
	boolean equals(other) {
		if (other instanceof AppUserAppRole) {
			other.appUserId == appUser?.id && other.appRoleId == appRole?.id
		}
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (appUser) builder.append(appUser.id)
		if (appRole) builder.append(appRole.id)
		builder.toHashCode()
	}

	static AppUserAppRole get(long appUserId, long appRoleId) {
		criteriaFor(appUserId, appRoleId).get()
	}

	static boolean exists(long appUserId, long appRoleId) {
		criteriaFor(appUserId, appRoleId).count()
	}

	private static DetachedCriteria criteriaFor(long appUserId, long appRoleId) {
		AppUserAppRole.where {
			appUser == AppUser.load(appUserId) &&
			appRole == AppRole.load(appRoleId)
		}
	}

	static AppUserAppRole create(AppUser appUser, AppRole appRole) {
		def instance = new AppUserAppRole(appUser: appUser, appRole: appRole)
		instance.save()
		instance
	}

	static boolean remove(AppUser u, AppRole r) {
		if (u != null && r != null) {
			AppUserAppRole.where { appUser == u && appRole == r }.deleteAll()
		}
	}

	static int removeAll(AppUser u) {
		u == null ? 0 : AppUserAppRole.where { appUser == u }.deleteAll()
	}

	static int removeAll(AppRole r) {
		r == null ? 0 : AppUserAppRole.where { appRole == r }.deleteAll()
	}

	static constraints = {
		appRole validator: { AppRole r, AppUserAppRole ur ->
			if (ur.appUser?.id) {
				AppUserAppRole.withNewSession {
					if (AppUserAppRole.exists(ur.appUser.id, r.id)) {
						return ['userRole.exists']
					}
				}
			}
		}
	}

	static mapping = {
		id composite: ['appUser', 'appRole']
		version false
	}
}
