import 'package:arch/arch.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:pacific_dashboards/models/school_enroll/school_enroll.dart';
import 'package:pacific_dashboards/models/school_enroll/school_enroll_chunk.dart';
import 'package:pacific_dashboards/pages/base/base_view_model.dart';
import 'package:pacific_dashboards/pages/individual_school/components/dashboards/components/enroll/enroll_data.dart';
import 'package:pacific_dashboards/utils/collections.dart';
import 'package:rxdart/rxdart.dart';

class EnrollViewModel extends BaseViewModel {
  final SchoolEnrollChunk _chunk;
  final Subject<EnrollData> _dataSubject = PublishSubject();

  EnrollViewModel(BuildContext ctx, SchoolEnrollChunk enrollChunk)
      : assert(enrollChunk != null),
        _chunk = enrollChunk,
        super(ctx);

  Stream<EnrollData> get dataStream => _dataSubject.stream;

  @override
  void onInit() {
    super.onInit();
    _dataSubject.disposeWith(disposeBag);
    _parseData();
  }

  void _parseData() {
    launchHandled(() async {
      final gradeHistory = await compute(
        _generateGradeDataHistory,
        _chunk.schoolData,
      );
      final gradeDataOnLastYear = gradeHistory.head;
      final gradeDataHistory = gradeHistory.tail;
      final genderByYearHistory = await compute(
        _generateGenderDataHistory,
        _chunk.schoolData,
      );
      final data = EnrollData(
        gradeDataOnLastYear: gradeDataOnLastYear,
        gradeDataHistory: gradeDataHistory,
        genderDataHistory: genderByYearHistory,
        femalePartOnLastYear: null,
        femalePartHistory: null,
      );
      _dataSubject.add(data);
    }, notifyProgress: true);
  }
}

List<EnrollDataByGradeHistory> _generateGradeDataHistory(
  List<SchoolEnroll> schoolData,
) {
  final List<EnrollDataByGradeHistory> results = [];
  final groupedByYear = schoolData.groupBy((it) => it.year);

  groupedByYear.forEach((year, enrollList) {
    final groupedByGrade = enrollList.groupBy((it) => it.classLevel);
    final List<EnrollDataByGrade> enrollInYear = [];

    groupedByGrade.forEach((grade, enrollList) {
      enrollInYear.addAll(enrollList.map((it) {
        return EnrollDataByGrade(
          grade: grade,
          male: it.enrollMale,
          female: it.enrollFemale,
          total: it.totalEnroll,
        );
      }));
    });

    results.add(EnrollDataByGradeHistory(
      year: year,
      data: enrollInYear,
    ));
  });
  return results.chainSort((lv, rv) => rv.year.compareTo(lv.year));
}

List<EnrollDataByYear> _generateGenderDataHistory(
  List<SchoolEnroll> schoolData,
) {
  final List<EnrollDataByYear> results = [];
  final groupedByYear = schoolData.groupBy((it) => it.year);

  groupedByYear.forEach((year, enrollList) {
    int femaleByYear = 0;
    int maleByYear = 0;
    int totalByYear = 0;
    for (var enrollData in enrollList) {
      femaleByYear += enrollData.enrollFemale;
      maleByYear += enrollData.enrollMale;
      totalByYear += enrollData.totalEnroll;
    }
    results.add(EnrollDataByYear(
      year: year,
      female: femaleByYear,
      male: maleByYear,
      total: totalByYear,
    ));
  });
  return results.chainSort((lv, rv) => lv.year.compareTo(rv.year));
}