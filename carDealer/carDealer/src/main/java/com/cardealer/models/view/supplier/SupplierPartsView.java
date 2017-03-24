package com.cardealer.models.view.supplier;

public class SupplierPartsView {

    private Long id;

    private String name;

    private boolean isImporter;

    private Integer partsSize;

    public SupplierPartsView() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }

    public Integer getPartsSize() {
        return partsSize;
    }

    public void setPartsSize(Integer partsSize) {
        this.partsSize = partsSize;
    }
}
