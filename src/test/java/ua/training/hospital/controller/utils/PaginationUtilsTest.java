package ua.training.hospital.controller.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class PaginationUtilsTest {
    PaginationUtils utils = new PaginationUtils();

    @Test
    public void testCheckPageNumber() {
        assertEquals(10,utils.checkPageNumber(10));
        assertEquals(0,utils.checkPageNumber(-1));
    }

    @Test
    public void testCheckRecordsPerPage() {
        assertEquals(10,utils.checkRecordsPerPage(10));
        assertEquals(1,utils.checkRecordsPerPage(-1));
    }
}