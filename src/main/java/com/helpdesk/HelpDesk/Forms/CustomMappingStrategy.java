package com.helpdesk.HelpDesk.Forms;

import com.opencsv.bean.BeanField;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.Comparator;

public class CustomMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {
    public boolean[] toShow;

    public CustomMappingStrategy(boolean[] toShow){
        this.toShow = toShow;
    }
    CustomMappingStrategy(){}
    @Override
    public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {

        super.setColumnMapping(new String[ FieldUtils.getAllFields(bean.getClass()).length]);
//        super.setColumnOrderOnWrite(new BeanComparator<>("Dependencia", "Numero de equipos","Numero de solicitudes",
//            "Numero de solicitudes excelentes",
//            "Numero de solicitudes buenas",
//            "Numero de solicitudes regulares",
//            "Numero de solicitudes malas",
//            "Numero de solicitudes deficientes"));
        final int numColumns = findMaxFieldIndex();
        if(toShow == null){
            toShow = new boolean[findMaxFieldIndex() + 1];
            for(int i = 0; i <= findMaxFieldIndex(); ++i){
                toShow[i] = true;
            }
        }
        if (!isAnnotationDriven() || numColumns == -1) {
            return super.generateHeader(bean);
        }
        int countTrue = 0;
        for(int i = 0; i < toShow.length; ++i){
            if(toShow[i]) countTrue++;
        }
        BeanField beanField;
        String[] header = new String[countTrue+1];
        beanField = findField(0);
        String columnHeaderName = extractHeaderName(beanField);
        header[0] = columnHeaderName;
        int counter = 1;
        for (int i = 0; i < toShow.length; i++) {
            if(toShow[i]){
                beanField = findField(i+1);
                columnHeaderName = extractHeaderName(beanField);
                header[counter++] = columnHeaderName;
            }
        }
        return header;
    }

    private String extractHeaderName(final BeanField<T> beanField) {
        if (beanField == null || beanField.getField() == null
                || beanField.getField().getDeclaredAnnotationsByType(CsvBindByName.class).length == 0) {
            return StringUtils.EMPTY;
        }

        final CsvBindByName bindByNameAnnotation = beanField.getField()
                .getDeclaredAnnotationsByType(CsvBindByName.class)[0];
        return bindByNameAnnotation.column();
    }

}