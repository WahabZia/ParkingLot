package com.parkinglot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parkinglot.data.Spots;

public interface SpotsRepository extends JpaRepository<Spots, Long> {

    public List<Spots> findAllByStatusAndFloorNoOrderByIdDesc(String status,Long id);
	
    @Query("SELECT COUNT(s) FROM Spots s WHERE s.status=?1")
	public int findCountByStatus(String status);

    @Modifying
    @Query("update Spots s set s.status =:status  where s.id=:id")
    void updateAccountStatus(@Param("id") Long id,@Param("status") String status );

}
