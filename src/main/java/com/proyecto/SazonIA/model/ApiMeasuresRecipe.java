package com.proyecto.SazonIA.model;

public class ApiMeasuresRecipe {
    

    private ApiMetricRecipe metric;

    private ApiUsRecipe us;

    public ApiMetricRecipe getMetric() {
        return metric;
    }

    public void setMetric(ApiMetricRecipe metric) {
        this.metric = metric;
    }

    public ApiUsRecipe getUs() {
        return us;
    }

    public void setUs(ApiUsRecipe us) {
        this.us = us;
    }

    
}
