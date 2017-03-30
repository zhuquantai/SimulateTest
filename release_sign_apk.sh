#!/usr/bin/env bash
java -jar ./signed_TCL/signapk.jar ./signed_TCL/platform.x509.pem ./signed_TCL/platform.pk8 ./app/build/outputs/apk/app-debug.apk ./app/build/outputs/apk/SimulateTest_signedAndroid.apk
#adb install -r /local/AndroidStudioProjects/SimulateTest/app/build/outputs/apk/SimulateTest_signedAndroid.apk

java -jar ./TCLSignApk/signapk.jar ./TCLSignApk/TCL_ReleaseKeys/platform.x509.pem ./TCLSignApk/TCL_ReleaseKeys/platform.pk8 ./app/build/outputs/apk/SimulateTest_signedAndroid.apk ./app/build/outputs/apk/SimulateTest_signedTcl.apk

#adb install -r /local/AndroidStudioProjects/SimulateTest/app/build/outputs/apk/SimulateTest_signedTcl.apk
adb remount
# adb shell mkdir /system/priv-app/SimulateTest
# adb shell rm /system/priv-app/SimulateTest/SimulateTest_signedTcl.apk
# adb push ./app/build/outputs/apk/SimulateTest_signedTcl.apk /system/priv-app/SimulateTest/
adb install -r ./app/build/outputs/apk/SimulateTest_signedTcl.apk
