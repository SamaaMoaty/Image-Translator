# Image-Translator
Image Translator Application for Desktop and Android Devices

Demo Video: https://www.youtube.com/watch?v=HkeE0_lPPAc&feature=youtu.be

>The application is used to translate different words from different languages through image. You can translate text in photos you've already taken. As well as taking a new photo in the Translate app and translate text on the captured photo using Optical Character Recognition (OCR) extracting text from images so that it can be edited, copied, formatted, indexed, searched, or translated covering many languages.


## Demo
   >![AppDemo](https://github.com/SamaaMoaty/Image-Translator-using-python/blob/master/demo.gif)
   
   
## Desktop Interface Version
   Using Python
   >Import Desktop Interface Folder in PyCharm

### Prerequisities:

- Python 3 (preferably python 3.7.0) 
  >Go to https://www.python.org/downloads/
- Download Tesseract 
  >Go to https://github.com/UB-Mannheim/tesseract/wiki and download the latest installer (version 5 at the time). Next, go to https://tesseract.patagames.com/langs/ and select the language file(s) you need if you are working with non-English language material (the languages are downloaded in the form of "language.traineddata" , after downloading these files copy them to tessdata folder which is inside the Tesseract-ocr folder that you've already downloaded.)
  
  > Note: tesseract path should be added to your .py file:   pytesseract.pytesseract.tesseract_cmd = r"C:\Your_Path\Tesseract-OCR\tesseract.exe" (you can find this line of code in windows.py file)

- PyQt5 (For the GUI)
  >> pip install pyqt5

- GoogleTrans Library (for Translation)
  >> pip install GoogleTrans

## Mobile Application version
  Using Java 
  >Import Mobile App Folder in Android Studio
  
 ### Prerequisities:
 
 - All you have to do is to install android studio on your device and import the project but for the Translation to function you will need to register in IBM cloud services to get the  Activation key(You can register for Lite services which is completely free and no billing account required for testing or upgrade for more functionality). 
 
 - OCR : https://developers.google.com/vision 
 
