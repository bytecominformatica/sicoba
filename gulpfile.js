var gulp = require('gulp'),
    gutil = require('gulp-util'),
    jshint = require('gulp-jshint'),
    concat = require('gulp-concat'),
    uglify = require('gulp-uglify'),
    sourcemaps = require('gulp-sourcemaps');

// define a tarefa padrao e adiciona a tarefa watch para ela
gulp.task('default', ['watch']);

// configura a tarefa jshint
gulp.task('jshint', function () {
    return gulp.src('client/app/**/*.js')
        .pipe(jshint())
        .pipe(jshint.reporter('jshint-stylish'));
});

// configura quais arquivos monitorar e qual tarefa chamar quando ouver alterações neles
gulp.task('watch', function () {
    gulp.watch('client/app/**/*.js', ['build-js']);
});

/** configura quais arquivos serao concatenados e minifica esses arquivos caso encontre
 * a propriedade --type production e escreve tudo isso no arquivo all.js
 * */
gulp.task('build-js', ['jshint'], function() {
    return gulp.src('client/app/**/*.js')
        .pipe(sourcemaps.init())
        .pipe(concat('app.js'))
        //only uglify if gulp is ran with '--type production'
        .pipe(gutil.env.type === 'production' ? uglify() : gutil.noop())
        .pipe(sourcemaps.write())
        .pipe(gulp.dest('client/dist/js'));
});