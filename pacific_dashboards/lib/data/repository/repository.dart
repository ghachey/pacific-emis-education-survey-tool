import 'package:pacific_dashboards/models/accreditations/accreditation_chunk.dart';
import 'package:pacific_dashboards/models/exam/exam.dart';
import 'package:pacific_dashboards/models/lookups/lookups.dart';
import 'package:pacific_dashboards/models/school/school.dart';
import 'package:pacific_dashboards/models/school_enroll/school_enroll_chunk.dart';
import 'package:pacific_dashboards/models/short_school/short_school.dart';
import 'package:pacific_dashboards/models/teacher/teacher.dart';

abstract class Repository {
  Stream<RepositoryResponse<List<Teacher>>> fetchAllTeachers();

  Stream<RepositoryResponse<List<School>>> fetchAllSchools();

  Stream<RepositoryResponse<List<Exam>>> fetchAllExams();

  Stream<RepositoryResponse<AccreditationChunk>> fetchAllAccreditations();

  Stream<Lookups> get lookups;

  Stream<RepositoryResponse<List<ShortSchool>>> fetchSchoolsList();

  Stream<RepositoryResponse<SchoolEnrollChunk>> fetchIndividualSchoolEnroll(
    String schoolId,
    String districtCode,
  );
}

abstract class RepositoryResponse<T> {
  const RepositoryResponse(this.data, this.throwable, this.type);

  final T data;
  final Object throwable;
  final RepositoryType type;
}

class SuccessRepositoryResponse<T> extends RepositoryResponse<T> {
  SuccessRepositoryResponse(RepositoryType type, T data)
      : super(data, null, type);
}

class FailureRepositoryResponse<T> extends RepositoryResponse<T> {
  FailureRepositoryResponse(RepositoryType type, Object throwable)
      : super(null, throwable, type);
}

enum RepositoryType { local, remote }
