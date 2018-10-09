#-verbose
-dontobfuscate

#Keep annotations
-keepattributes *Annotation*

#Team Code
-keep,includedescriptorclasses class com.hazenrobotics.** {
    *;
}

-dontwarn com.sun.tools.**
-dontwarn com.google.common.**
-dontwarn com.google.gson.**
-dontwarn com.google.errorprone.**
-dontwarn com.qualcomm.analytics.**
-dontwarn org.apache.commons.math3.geometry.euclidean.twod.Line