package org.springframework.orm.jpa;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;

import org.springframework.lang.Nullable;

/**
 * Metadata interface for a Spring-managed JPA {@link EntityManagerFactory}.
 *
 * <p>This facility can be obtained from Spring-managed EntityManagerFactory
 * proxies through casting the EntityManagerFactory handle to this interface.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 2.0
 */
public interface EntityManagerFactoryInfo {

	/**
	 * Return the underlying PersistenceProvider that the underlying
	 * EntityManagerFactory was created with.
	 *
	 * @return the PersistenceProvider used to create this EntityManagerFactory,
	 * or {@code null} if the standard JPA provider autodetection process
	 * was used to configure the EntityManagerFactory
	 */
	@Nullable
	PersistenceProvider getPersistenceProvider();

	/**
	 * Return the PersistenceUnitInfo used to create this
	 * EntityManagerFactory, if the in-container API was used.
	 *
	 * @return the PersistenceUnitInfo used to create this EntityManagerFactory,
	 * or {@code null} if the in-container contract was not used to
	 * configure the EntityManagerFactory
	 */
	@Nullable
	PersistenceUnitInfo getPersistenceUnitInfo();

	/**
	 * Return the name of the persistence unit used to create this
	 * EntityManagerFactory, or {@code null} if it is an unnamed default.
	 * <p>If {@code getPersistenceUnitInfo()} returns non-null, the result of
	 * {@code getPersistenceUnitName()} must be equal to the value returned by
	 * {@code PersistenceUnitInfo.getPersistenceUnitName()}.
	 *
	 * @see #getPersistenceUnitInfo()
	 * @see javax.persistence.spi.PersistenceUnitInfo#getPersistenceUnitName()
	 */
	@Nullable
	String getPersistenceUnitName();

	/**
	 * Return the JDBC DataSource that this EntityManagerFactory
	 * obtains its JDBC Connections from.
	 *
	 * @return the JDBC DataSource, or {@code null} if not known
	 */
	@Nullable
	DataSource getDataSource();

	/**
	 * Return the (potentially vendor-specific) EntityManager interface
	 * that this factory's EntityManagers will implement.
	 * <p>A {@code null} return value suggests that autodetection is supposed
	 * to happen: either based on a target {@code EntityManager} instance
	 * or simply defaulting to {@code javax.persistence.EntityManager}.
	 */
	@Nullable
	Class<? extends EntityManager> getEntityManagerInterface();

	/**
	 * Return the vendor-specific JpaDialect implementation for this
	 * EntityManagerFactory, or {@code null} if not known.
	 */
	@Nullable
	JpaDialect getJpaDialect();

	/**
	 * Return the ClassLoader that the application's beans are loaded with.
	 * <p>Proxies will be generated in this ClassLoader.
	 */
	ClassLoader getBeanClassLoader();

	/**
	 * Return the raw underlying EntityManagerFactory.
	 *
	 * @return the unadorned EntityManagerFactory (never {@code null})
	 */
	EntityManagerFactory getNativeEntityManagerFactory();

	/**
	 * Create a native JPA EntityManager to be used as the framework-managed
	 * resource behind an application-level EntityManager handle.
	 * <p>This exposes a native {@code EntityManager} from the underlying
	 * {@link #getNativeEntityManagerFactory() native EntityManagerFactory},
	 * taking {@link JpaVendorAdapter#postProcessEntityManager(EntityManager)}
	 * into account.
	 *
	 * @see #getNativeEntityManagerFactory()
	 * @see EntityManagerFactory#createEntityManager()
	 * @since 5.3
	 */
	EntityManager createNativeEntityManager(@Nullable Map<?, ?> properties);

}
