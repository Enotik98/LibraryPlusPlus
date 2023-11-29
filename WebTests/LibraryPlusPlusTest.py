import time

from Driver import Driver


from selenium.webdriver.common.by import By

#login to library plus plus system
def login (driver : Driver, email : str, password : str):
    time.sleep(2)
    driver.go_to_page('/login')

    email_input = driver.find_element(By.NAME, 'login-email')
    password_input = driver.find_element(By.NAME, 'login-password')
    login_button = driver.find_element(By.NAME, 'login')

    email_input.send_keys(email)
    time.sleep(1)
    password_input.send_keys(password)
    time.sleep(1)
    login_button.click()

def go_to_product(driver : Driver):
    time.sleep(5)

    link = driver.find_element(By.CSS_SELECTOR, '.card:first-child .card_footer > a')
    link.click()

def order_book(driver : Driver, duration):
    time.sleep(5)

    duration_select = driver.find_element(By.NAME, 'order-duration')
    order_button = driver.find_element(By.NAME, 'order')

    time.sleep(1)
    duration_select.send_keys(duration)
    time.sleep(1)
    order_button.click()
    time.sleep(5)


if __name__ == '__main__':
    driver = Driver('http://localhost:8082')
    login(driver, "tesgmail.com", "")
    login(driver, "test1@gmail.com", " password")
    login(driver, "tes@gmail.com", "password")
    login(driver, "test1@gmail.com", "password")
    go_to_product(driver)
    order_book(driver, '2 week')

    login(driver, "bilka@gmail.com", "123")
    go_to_product(driver)
    order_book(driver, '1 month')

    login(driver, "enotik@gmail.com", "123")
    go_to_product(driver)
    order_book(driver, '3 week')

    driver.quit()
