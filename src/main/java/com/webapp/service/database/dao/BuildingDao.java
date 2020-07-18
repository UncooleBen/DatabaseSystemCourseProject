package com.webapp.service.database.dao;

import com.webapp.model.Building;

import java.util.List;

/**
 * This interface declares methods used to interact with table 't_building' in the database.
 *
 * @author Juntao Peng (original creator)
 * @author Shangzhen Li (refactor)
 */
public interface BuildingDao {

    /**
     * Get a limited number of buildings' records from database.
     *
     * @param skip The building to skip
     * @param limit an integer to limit the number of records returned from database
     * @return a List (may be empty) with Building objects
     */
    List<Building> listBuilding(int skip, int limit);

    /**
     * Get the total number of buildings' records from database.
     *
     * @return An int of total number
     */
    int queryNumberOfBuildings();

    /**
     * Get required building's record according to the given building's id.
     *
     * @param id an integer as Building's id
     * @return a Building object (may be null)
     */
    Building queryBuildingById(int id);

    /**
     * Insert a building's record into database.
     *
     * @param building a Building object with information to be inserted
     * @return true if succeed, false otherwise
     */
    boolean addBuilding(Building building);

    /**
     * Delete a building's record from database according to the given id.
     *
     * @param id an integer as the building's id to be deleted
     * @return true if succeed, false otherwise
     */
    boolean deleteBuilding(int id);

    /**
     * Update a building's record and the id remains the same.
     *
     * @param building a Building object with information to be updated
     * @return true if succeed, false otherwise
     */
    boolean updateBuilding(Building building);
}
