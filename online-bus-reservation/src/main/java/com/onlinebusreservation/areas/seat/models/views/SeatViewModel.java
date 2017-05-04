package com.onlinebusreservation.areas.seat.models.views;

public class SeatViewModel implements Comparable<SeatViewModel> {

    private Integer seatNumber;

    public SeatViewModel() {
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public int compareTo(SeatViewModel o) {

        if (this.seatNumber == o.seatNumber) {

            return 0;

        } else if (this.seatNumber >  o.seatNumber) {

            return 1;

        } else{

            return -1;
        }
    }
}
