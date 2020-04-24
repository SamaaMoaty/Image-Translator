import sys
from windows import TranslatorGUI
from PyQt5.QtWidgets import *


app = QApplication(sys.argv)
window = TranslatorGUI()
sys.exit(app.exec_())