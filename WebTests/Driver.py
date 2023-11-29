import time

from selenium import webdriver
from selenium.webdriver.common.by import By

class Driver:
    
    def __init__(self, baseUrl):
        self.driver = webdriver.Chrome()
        self.BASE_URL = baseUrl

    def quit (self):
        self.driver.quit()
    
    def go_to_page(self, uri = ''):
        time.sleep(3)
        self.driver.get(self.BASE_URL + uri)
    
    def find_element(self, by, value):
        return self.driver.find_element(by, value)
