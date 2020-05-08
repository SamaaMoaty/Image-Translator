import sys
from windows import TranslatorGUI
from PyQt5.QtWidgets import *

# App driver calling TranslatorGUI in windows.py

app = QApplication(sys.argv)
window = TranslatorGUI()
sys.exit(app.exec_())
