package fm.doe.national.data.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import fm.doe.national.data.persistence.dao.AnswerDao;
import fm.doe.national.data.persistence.dao.CategoryDao;
import fm.doe.national.data.persistence.dao.CriteriaDao;
import fm.doe.national.data.persistence.dao.PhotoDao;
import fm.doe.national.data.persistence.dao.SchoolDao;
import fm.doe.national.data.persistence.dao.StandardDao;
import fm.doe.national.data.persistence.dao.SubCriteriaDao;
import fm.doe.national.data.persistence.dao.SurveyDao;
import fm.doe.national.data.persistence.entity.RoomAnswer;
import fm.doe.national.data.persistence.entity.RoomCategory;
import fm.doe.national.data.persistence.entity.RoomCriteria;
import fm.doe.national.data.persistence.entity.RoomPhoto;
import fm.doe.national.data.persistence.entity.RoomSchool;
import fm.doe.national.data.persistence.entity.RoomStandard;
import fm.doe.national.data.persistence.entity.RoomSubCriteria;
import fm.doe.national.data.persistence.entity.RoomSurvey;

@Database(
        entities = {
                RoomSurvey.class,
                RoomCategory.class,
                RoomStandard.class,
                RoomCriteria.class,
                RoomSubCriteria.class,
                RoomAnswer.class,
                RoomPhoto.class,
                RoomSchool.class
        },
        version = 1,
        exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract SurveyDao getSurveyDao();

    public abstract CategoryDao getCategoryDao();

    public abstract StandardDao getStandardDao();

    public abstract CriteriaDao getCriteriaDao();

    public abstract SubCriteriaDao getSubcriteriaDao();

    public abstract PhotoDao getPhotoDao();

    public abstract AnswerDao getAnswerDao();

    public abstract SchoolDao getSchoolDao();
}