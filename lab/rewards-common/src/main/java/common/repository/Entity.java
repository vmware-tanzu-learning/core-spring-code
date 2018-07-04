package common.repository;

/**
 * A base class for all entities that use a internal long identifier for
 * tracking entity identity.
 */
public class Entity {

	private Long entityId;

	/**
	 * Returns the entity identifier used to internally distinguish this entity
	 * among other entities of the same type in the system. Should typically
	 * only be called by privileged data access infrastructure code such as an
	 * Object Relational Mapper (ORM) and not by application code.
	 * 
	 * @return the internal entity identifier
	 */
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Sets the internal entity identifier - should only be called by privileged
	 * data access code: repositories that work with an Object Relational Mapper
	 * (ORM). Should <i>never</i> be set by application code explicitly.
	 * 
	 * @param entityId
	 *            the internal entity identifier
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
}
