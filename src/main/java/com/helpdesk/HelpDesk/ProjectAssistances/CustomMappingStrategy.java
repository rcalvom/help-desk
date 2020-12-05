package com.helpdesk.HelpDesk.ProjectAssistances;

import com.opencsv.bean.BeanField;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;


public class CustomMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {

    public boolean[] toShow;

    public CustomMappingStrategy(boolean[] toShow){
        this.toShow = toShow;
    }

    @Override
    public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {
        super.setColumnMapping(new String[ FieldUtils.getAllFields(bean.getClass()).length]);
        final int numColumns = findMaxFieldIndex();

        if(this.toShow == null){
            this.toShow = new boolean[findMaxFieldIndex() + 1];
            for(int i = 0; i <= findMaxFieldIndex(); ++ i){
                this.toShow[i] = true;
            }
        }

        if (!isAnnotationDriven() || numColumns == -1) {
            return super.generateHeader(bean);
        }

        int countTrue = 0;

        for (boolean b : this.toShow) {
            if (b) countTrue++;
        }

        BeanField<T> beanField;
        String[] header = new String[countTrue + 1];
        beanField = findField(0);
        String columnHeaderName = extractHeaderName(beanField);
        header[0] = columnHeaderName;
        int counter = 1;
        for (int i = 0; i < this.toShow.length; i++) {
            if(this.toShow[i]){
                beanField = findField(i + 1);
                columnHeaderName = extractHeaderName(beanField);
                header[counter++] = columnHeaderName;
            }
        }
        return header;
    }

    private String extractHeaderName(final BeanField<T> beanField) {
        if (beanField == null || beanField.getField() == null || beanField.getField().getDeclaredAnnotationsByType(CsvBindByName.class).length == 0) {
            return StringUtils.EMPTY;
        }
        final CsvBindByName bindByNameAnnotation = beanField.getField().getDeclaredAnnotationsByType(CsvBindByName.class)[0];
        return bindByNameAnnotation.column();
    }

}