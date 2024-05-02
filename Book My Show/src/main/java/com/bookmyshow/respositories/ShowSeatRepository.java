package com.bookmyshow.respositories;

import com.bookmyshow.models.Show;
import com.bookmyshow.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    @Override
    List<ShowSeat> findAllById(Iterable<Long> showSeatIds);

    @Override
    ShowSeat save(ShowSeat showSeat); //If we are creating a new object then this will insert in the db else it will update in the db.
}
