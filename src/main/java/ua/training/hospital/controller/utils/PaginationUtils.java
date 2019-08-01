package ua.training.hospital.controller.utils;

import org.springframework.stereotype.Component;

@Component
public class PaginationUtils {
    public int checkPageNumber(int pageNumber){
        return pageNumber < 0 ? 0 : pageNumber;
    }

    public int checkRecordsPerPage(int recordsPerPage){
        return recordsPerPage < 1 ? 1 : recordsPerPage;
    }
}
