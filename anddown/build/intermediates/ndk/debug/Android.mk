LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := anddown
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	C:\Users\david\AndroidStudioProjects\Reddit_android\anddown\jni\anddown.c \
	C:\Users\david\AndroidStudioProjects\Reddit_android\anddown\jni\Android.mk \
	C:\Users\david\AndroidStudioProjects\Reddit_android\anddown\jni\Application.mk \
	C:\Users\david\AndroidStudioProjects\Reddit_android\anddown\jni\html_block_names.txt \
	C:\Users\david\AndroidStudioProjects\Reddit_android\anddown\jni\src\autolink.c \
	C:\Users\david\AndroidStudioProjects\Reddit_android\anddown\jni\src\buffer.c \
	C:\Users\david\AndroidStudioProjects\Reddit_android\anddown\jni\src\document.c \
	C:\Users\david\AndroidStudioProjects\Reddit_android\anddown\jni\src\escape.c \
	C:\Users\david\AndroidStudioProjects\Reddit_android\anddown\jni\src\html.c \
	C:\Users\david\AndroidStudioProjects\Reddit_android\anddown\jni\src\html_blocks.c \
	C:\Users\david\AndroidStudioProjects\Reddit_android\anddown\jni\src\html_smartypants.c \
	C:\Users\david\AndroidStudioProjects\Reddit_android\anddown\jni\src\stack.c \
	C:\Users\david\AndroidStudioProjects\Reddit_android\anddown\jni\src\version.c \

LOCAL_C_INCLUDES += C:\Users\david\AndroidStudioProjects\Reddit_android\anddown\jni
LOCAL_C_INCLUDES += C:\Users\david\AndroidStudioProjects\Reddit_android\anddown\jni\src
LOCAL_C_INCLUDES += C:\Users\david\AndroidStudioProjects\Reddit_android\anddown\build-types\debug\jni

include $(BUILD_SHARED_LIBRARY)
