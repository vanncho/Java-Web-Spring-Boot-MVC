package com.residentevil.services;

import com.residentevil.entities.Capital;
import com.residentevil.entities.Virus;
import com.residentevil.entities.enumerations.Magnitude;
import com.residentevil.entities.enumerations.Mutation;
import com.residentevil.mappers.ModelParser;
import com.residentevil.models.binding.virus.AddVirusModel;
import com.residentevil.models.binding.virus.UpdateVirusModel;
import com.residentevil.models.view.virus.EditVirusView;
import com.residentevil.models.view.virus.VirusShowView;
import com.residentevil.repositories.CapitalRepository;
import com.residentevil.repositories.VirusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

@Service
public class VirusServiceImpl implements VirusService {

    @Autowired
    private VirusRepository virusRepository;

    @Autowired
    private CapitalRepository capitalRepository;

    @Autowired
    private ModelParser modelParser;

    @Override
    public List<VirusShowView> getAllViruses() {

        List<Virus> viruses = this.virusRepository.findAll();
        List<VirusShowView> virusShowViews = new ArrayList<>();

        for (Virus virus : viruses) {

            VirusShowView virusShowView = this.modelParser.convert(virus, VirusShowView.class);
            virusShowViews.add(virusShowView);
        }

        return virusShowViews;
    }

    @Override
    public void addVirus(AddVirusModel addVirusModel) {

        Virus virus = new Virus();
        virus.setName(addVirusModel.getName());
        virus.setDescription(addVirusModel.getDescription());
        virus.setCreator(addVirusModel.getCreator());
        virus.setSideEffects(addVirusModel.getSideEffects());
        virus.setIsDeadly(addVirusModel.getIsDeadly());
        virus.setIsCurable(addVirusModel.getIsCurable());
        Mutation mutation = Mutation.valueOf(addVirusModel.getMutation().toUpperCase());
        virus.setMutation(mutation);
        virus.setTurnoverRate(addVirusModel.getTurnoverRate());
        virus.setHoursUntilTurn(addVirusModel.getHoursUntilTurn());
        Magnitude magnitude = Magnitude.valueOf(addVirusModel.getMagnitude().toUpperCase());
        virus.setMagnitude(magnitude);

        DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
        Date date = null;

        try {
            date = format.parse(addVirusModel.getReleasedOn());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        virus.setReleasedOn(date);

        List<String> capitals = addVirusModel.getCapitals();

        for (String capital : capitals) {

            Capital currCapital = this.capitalRepository.findByName(capital);
            virus.getCapitals().add(currCapital);
        }

        this.virusRepository.save(virus);
    }

    @Override
    public EditVirusView getById(Long id) {

        Virus virus = this.virusRepository.findOne(id);
        EditVirusView editVirusView = new EditVirusView();

        editVirusView.setName(virus.getName());
        editVirusView.setDescription(virus.getDescription());
        editVirusView.setCreator(virus.getCreator());
        editVirusView.setSideEffects(virus.getSideEffects());
        editVirusView.setIsDeadly(virus.getIsDeadly());
        editVirusView.setIsCurable(virus.getIsCurable());
        Mutation mutation = virus.getMutation();
        editVirusView.setMutation(String.valueOf(mutation));
        editVirusView.setTurnoverRate(virus.getTurnoverRate());
        editVirusView.setHoursUntilTurn(virus.getHoursUntilTurn());
        Magnitude magnitude = virus.getMagnitude();
        editVirusView.setMagnitude(String.valueOf(magnitude));
        String date = String.valueOf(virus.getReleasedOn());
        editVirusView.setReleasedOn(date);

//        Set<Capital> capitals = virus.getCapitals();
        List<Capital> capitals = this.capitalRepository.findAll();
        List<String> capitalsAsString = new ArrayList<>();

        for (Capital capital : capitals) {

            String currCapital = capital.getName();
            capitalsAsString.add(currCapital);
        }

        editVirusView.setCapitals(capitalsAsString);

        return editVirusView;
    }

    @Override
    public void updateVirus(Long id, UpdateVirusModel updateVirusModel) {

        String name = updateVirusModel.getName();
        String description = updateVirusModel.getDescription();
        String creator = updateVirusModel.getCreator();
        String sideEffects = updateVirusModel.getSideEffects();
        boolean isDeadly = updateVirusModel.getIsDeadly();
        boolean isCurable = updateVirusModel.getIsCurable();
        Mutation mutation = Mutation.valueOf(updateVirusModel.getMutation().toUpperCase());
        Double turnoverRate = updateVirusModel.getTurnoverRate();
        Integer hoursUntilTurn = updateVirusModel.getHoursUntilTurn();
        Magnitude magnitude = Magnitude.valueOf(updateVirusModel.getMagnitude().toUpperCase());

        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;

        try {
            date = format.parse(updateVirusModel.getReleasedOn());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<String> capitals = updateVirusModel.getCapitals();
        Virus virus = this.virusRepository.findOne(id);
        virus.getCapitals().clear();

        for (String capital : capitals) {

            Capital currCapital = this.capitalRepository.findByName(capital);
            virus.getCapitals().add(currCapital);
        }

        this.virusRepository.updateVirus(id,
                name,
                description,
                creator,
                sideEffects,
                isDeadly,
                isCurable,
                mutation,
                turnoverRate,
                hoursUntilTurn,
                magnitude,
                date);
    }

    @Override
    public void deleteVirus(Long id) {

        this.virusRepository.delete(id);
    }

    @Override
    public String getGeoData() {

        List<Virus> viruses = this.virusRepository.findAll();

        StringBuilder sb = new StringBuilder();
        StringJoiner joiner = new StringJoiner(",");

        String header = "{\n" +
                "\"type\": \"FeatureCollection\",\n" +
                "    \"features\": [\n";

        String footer = "]\n" +
                "}";

        sb.append(header);

        for (Virus virus : viruses) {

            String color = "#f00";
            int magnitude = 0;

            switch (virus.getMagnitude()) {

                case LOW:
                    magnitude = 4;
                    break;
                case MEDIUM:
                    magnitude = 5;
                    break;
                case HIGH:
                    magnitude = 6;
                    break;
            }

            for (Capital capital : virus.getCapitals()) {

                String body = String.format("{\n" +
                                "        \"type\": \"Feature\",\n" +
                                "        \"properties\": {\n" +
                                "            \"mag\": %d,\n" +
                                "            \"color\": \"%s\"\n" +
                                "        },\n" +
                                "        \"geometry\": {\n" +
                                "            \"type\": \"Point\",\n" +
                                "            \"coordinates\": [\n" +
                                "                %f,\n" +
                                "                %f\n" +
                                "            ]\n" +
                                "        }\n" +
                                "    }\n", magnitude, color, capital.getLatitude(), capital.getLongitude());
                joiner.add(body);
            }
        }

        sb.append(joiner);
        sb.append(footer);
        return sb.toString();
    }
}
