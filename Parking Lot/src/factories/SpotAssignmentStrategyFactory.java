package factories;

import models.SpotAssignmentStrategyType;
import repositories.ParkingLotRepository;
import strategies.CheapestSpotAssignmentStrategy;
import strategies.NearestSpotAssignmentStrategy;
import strategies.RandomSpotAssignmentStrategy;
import strategies.SpotAssignmentStrategy;

public class SpotAssignmentStrategyFactory {
    //This is to create the right strategy for the user

    public static SpotAssignmentStrategy getSpotAssignmentStrategy(SpotAssignmentStrategyType spotAssignmentStrategyType, ParkingLotRepository parkingLotRepository) {
        SpotAssignmentStrategy spotAssignmentStrategy;

        switch (spotAssignmentStrategyType) {
            case CHEAPEST:
                spotAssignmentStrategy = new CheapestSpotAssignmentStrategy();
                break;
            case NEAREST:
                spotAssignmentStrategy = new NearestSpotAssignmentStrategy();
                break;
            default:
                spotAssignmentStrategy = new RandomSpotAssignmentStrategy(parkingLotRepository);
        }

        return spotAssignmentStrategy;
    }
}
