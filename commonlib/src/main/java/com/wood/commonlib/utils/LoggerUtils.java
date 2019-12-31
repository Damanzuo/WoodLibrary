package com.wood.commonlib.utils;

public class LoggerUtils {
    private static LoggerUtils mLoggerUtils;

    private LoggerUtils() {
        super();
    }

    public static LoggerUtils getInstance() {
        if (null == mLoggerUtils) {
            mLoggerUtils = new LoggerUtils();
        }
        return mLoggerUtils;
    }

    public void initLogger(boolean isSaveFile){
//        FormatStrategy logFormatStrategy = PrettyFormatStrategy.newBuilder()
//                .showThreadInfo(isShowThreadInfo())
//                .methodCount(methodCount())
//                .methodOffset(methodOffset())
//                .tag(String.format(getString(R.string.global_tag), getGlobalTag()))
//                .build();
//        LogcatLogStrategy l = new LogcatLogStrategy();
//        Logger.addLogAdapter(new AndroidLogAdapter(logFormatStrategy) {
//            @Override
//            public boolean isLoggable(int priority, @Nullable String tag) {
////                if (isOnlyDebugPrintLog()) {
//                return !isOnlyDebugPrintLog() || BuildConfig.DEBUG;
////                }
////                return super.isLoggable(priority, tag);
//            }
//
//            @Override
//            public void log(int priority, @Nullable String tag, @NonNull String message) {
//                if (getLogLevel() > priority) {
//                    return;
//                }
//                super.log(priority, tag, message);
//            }
//        });
//
//        if (isSaveFile()) {
//            //默认存储路径为 /sdcard/logger/xxx.cvs
//            FormatStrategy fileFormatStrategy = CsvFormatStrategy.newBuilder()
//                    .tag(String.format(getString(R.string.global_tag), getGlobalTag()))
//                    .build();
//            Logger.addLogAdapter(new DiskLogAdapter(fileFormatStrategy) {
//                @Override
//                public boolean isLoggable(int priority, @Nullable String tag) {
////                    if (isOnlyDebugPrintLog()) {
//                    return !isOnlyDebugPrintLog() || BuildConfig.DEBUG;
////                    }
////                    return super.isLoggable(priority, tag);
//                }
//
//                @Override
//                public void log(int priority, @Nullable String tag, @NonNull String message) {
//                    if (getLogLevel() > priority) {
//                        return;
//                    }
//                    super.log(priority, tag, message);
//                }
//            });
//        }
    }
}
