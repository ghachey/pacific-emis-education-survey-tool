import 'package:flutter/material.dart';

import 'ui/charts_grid.dart';

class App extends StatelessWidget {
  final _appName = 'Custom Charts';

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: _appName,
      theme: ThemeData(
        brightness: Brightness.dark,
        primaryColor: Colors.deepPurple[800],
        accentColor: Colors.deepOrangeAccent[100],
        fontFamily: 'Montserrat',
        textTheme: TextTheme(
          headline: TextStyle(fontSize: 72.0, fontWeight: FontWeight.bold),
          title: TextStyle(fontSize: 36.0, fontStyle: FontStyle.italic),
          body1: TextStyle(fontSize: 14.0, fontFamily: 'Hind'),
        ),
      ),
      home: Scaffold(
        appBar: AppBar(
          title: Text('Charts'),
        ),
        body: ChartsGrid(),
      ),
    );
  }
}
