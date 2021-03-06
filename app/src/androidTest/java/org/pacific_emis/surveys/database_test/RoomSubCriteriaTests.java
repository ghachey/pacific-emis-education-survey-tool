package org.pacific_emis.surveys.database_test;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import org.pacific_emis.surveys.core.data.persistence.dao.CategoryDao;
import org.pacific_emis.surveys.core.data.persistence.dao.CriteriaDao;
import org.pacific_emis.surveys.core.data.persistence.dao.StandardDao;
import org.pacific_emis.surveys.core.data.persistence.dao.SubCriteriaDao;
import org.pacific_emis.surveys.core.data.persistence.dao.SurveyDao;
import org.pacific_emis.surveys.core.data.persistence.entity.RoomSubCriteria;
import org.pacific_emis.surveys.database_test.util.RoomTestData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class RoomSubCriteriaTests extends BaseDatabaseTest {

    private SubCriteriaDao subCriteriaDao;

    private long testCriteriaId = -1;

    @Before
    @Override
    public void before() {
        super.before();
        subCriteriaDao = database.getSubcriteriaDao();
        fillTable();
    }

    private void fillTable() {
        SurveyDao surveyDao = database.getSurveyDao();
        surveyDao.insert(RoomTestData.getSurveyFor_putSurveyEntityTest());
        long testSurveyId = surveyDao.getAll().get(0).uid;

        CategoryDao categoryDao = database.getCategoryDao();
        categoryDao.insert(RoomTestData.getCategoryFor_createDeleteTest(testSurveyId));
        long testCategoryId = categoryDao.getAllForSurveyWithId(testSurveyId).get(0).uid;

        StandardDao standardDao = database.getStandardDao();
        standardDao.insert(RoomTestData.getTestStandard(testCategoryId));
        long testStandardId = standardDao.getAllForCategoryWithId(testCategoryId).get(0).uid;


        CriteriaDao criteriaDao = database.getCriteriaDao();
        criteriaDao.insert(RoomTestData.getTestCriteria(testStandardId));
        testCriteriaId = criteriaDao.getAllForStandardWithId(testStandardId).get(0).uid;
    }

    @Test
    public void createDeleteTest() {
        subCriteriaDao.deleteAllForCriteriaWithId(testCriteriaId);

        RoomSubCriteria subCriteria = RoomTestData.getTestSubCriteria(testCriteriaId);

        subCriteriaDao.insert(subCriteria);
        assertEquals(1, subCriteriaDao.getAllForCriteriaWithId(testCriteriaId).size());

        subCriteria.title = "RoomSubCriteria 2";
        subCriteriaDao.insert(subCriteria);

        List<RoomSubCriteria> subCriteriasInDb = subCriteriaDao.getAllForCriteriaWithId(testCriteriaId);
        assertEquals(2, subCriteriasInDb.size());
        assertEquals("RoomSubCriteria 2", subCriteriasInDb.get(1).title);

        subCriteriaDao.delete(subCriteriasInDb.get(0));

        subCriteriasInDb = subCriteriaDao.getAllForCriteriaWithId(testCriteriaId);
        assertEquals(1, subCriteriasInDb.size());
        assertEquals("RoomSubCriteria 2", subCriteriasInDb.get(0).title);

        subCriteriaDao.insert(subCriteria);
        subCriteriaDao.deleteAllForCriteriaWithId(testCriteriaId);

        subCriteriasInDb = subCriteriaDao.getAllForCriteriaWithId(testCriteriaId);
        assertEquals(0, subCriteriasInDb.size());
    }

    @Test
    public void updateTest() {
        subCriteriaDao.deleteAllForCriteriaWithId(testCriteriaId);

        subCriteriaDao.insert(RoomTestData.getTestSubCriteria(testCriteriaId));
        RoomSubCriteria subCriteria = subCriteriaDao.getAllForCriteriaWithId(testCriteriaId).get(0);
        assertEquals("RoomSubCriteria One", subCriteria.title);

        subCriteria.title = "RoomSubCriteria Two";
        subCriteriaDao.update(subCriteria);

        subCriteria = subCriteriaDao.getAllForCriteriaWithId(testCriteriaId).get(0);
        assertEquals("RoomSubCriteria Two", subCriteria.title);
    }

    @Test
    public void getByIdTest() {
        subCriteriaDao.deleteAllForCriteriaWithId(testCriteriaId);
        subCriteriaDao.insert(RoomTestData.getTestSubCriteria(testCriteriaId));

        RoomSubCriteria subCriteria = subCriteriaDao.getAllForCriteriaWithId(testCriteriaId).get(0);
        RoomSubCriteria subCriteriaById = subCriteriaDao.getById(subCriteria.uid);

        assertEquals(subCriteria.title, subCriteriaById.title);
        assertNull(subCriteriaDao.getById(123984));
    }

    @Test
    public void cascadeDeleteTest() {
        subCriteriaDao.deleteAllForCriteriaWithId(testCriteriaId);

        subCriteriaDao.insert(RoomTestData.getTestSubCriteria(testCriteriaId));
        subCriteriaDao.insert(RoomTestData.getTestSubCriteria(testCriteriaId));
        subCriteriaDao.insert(RoomTestData.getTestSubCriteria(testCriteriaId));
        subCriteriaDao.insert(RoomTestData.getTestSubCriteria(testCriteriaId));
        subCriteriaDao.insert(RoomTestData.getTestSubCriteria(testCriteriaId));
        subCriteriaDao.insert(RoomTestData.getTestSubCriteria(testCriteriaId));

        List<RoomSubCriteria> insertedSubCriterias = subCriteriaDao.getAllForCriteriaWithId(testCriteriaId);
        assertEquals(6, insertedSubCriterias.size());

        database.getCategoryDao().deleteById(testCriteriaId);

        assertEquals(0, subCriteriaDao.getAllForCriteriaWithId(testCriteriaId).size());

        insertedSubCriterias.forEach(standard -> assertNull(subCriteriaDao.getById(standard.uid)));

        fillTable();
    }
}