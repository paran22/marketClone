package com.example.marketclone.testData;

import com.example.marketclone.model.Product;
import com.example.marketclone.requestDto.ProductRequestDto;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class SeleniumRunner {

    public List<Product> getProducts() {

        // 실행시킬 웹 브라우저의 경로 ( 프로젝트 경로 + 자세한 경로 )
                // System.getProperty() : 시스템 정보를 가져올 때 사용
                // "user.dir"을 넣으면 현재 디렉토리 출력
        Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/chromedriver_win32/chromedriver.exe");

        // WebDriver의 실행 경로 설정
        System.setProperty("webdriver.chrome.driver", path.toString());

        // WebDriver의 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");            // 전체화면으로 실행
        options.addArguments("--disable-popup-blocking");    // 팝업 무시
        options.addArguments("--disable-default-apps");     // 기본앱 사용안함
        options.addArguments("headless");                   // 브라우저 실행이 내부로 전환, 눈에 안보인다.
                                                            // 중간에 에러가 발생하면 브라우저가 종료되지 않으므로 기능 구현 중에는 보이게 해주는게 좋다.

        // WebDriver 객체 생성
        ChromeDriver driver = new ChromeDriver(options);

        // 웹페이지 요청 (마켓컬리 - 베스트 )
        driver.get("https://www.kurly.com/shop/goods/goods_list.php?category=029");


        // 브라우저가 실행되는 시간을 기다려준다.
                // 브라우저가 열리기도 전에 태그를 가져올 수 있기 때문에 1초 정도 기다리는 시간을 설정해놓는게 좋다.
                // (설정 안하면 될 때도 있고 안될 때도 있다)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // CssSelector로 상품의 name, price, desc, img들을 가져와서 리스트로 만든다.
        List<WebElement> names = driver.findElementsByCssSelector("a.info span.name");
        List<WebElement> prices = driver.findElementsByCssSelector("a.info span.price");
        List<WebElement> descs = driver.findElementsByCssSelector("a.info span.desc");
        List<WebElement> imgs = driver.findElementsByCssSelector("div.thumb a.img img");

        // Product들을 담아서 반환해줄 리스트 객체 생성
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {

            // 값이 하나라도 들어오지 않으면 건너 뛰기
            if (names.get(i) == null || prices.get(i) == null || descs.get(i) == null || imgs.get(i) == null) {
                System.out.println("값이 존재하지 않습니다.");
                continue;
            }

            // name
            String name = names.get(i).getText();

            // price - "1,900원" 이렇게 String 형식으로 들어오기 때문에 원하는 형태로 변형시켜준다.
            int price = Integer.parseInt(prices.get(i).getText()
                    .replaceAll(",", "").replaceAll("원", ""));

            // desc
            String desc = descs.get(i).getText();

            // img의 url
            String img = imgs.get(i).getAttribute("src");

            // Product 생성해서 리스트에 담는 과정
            ProductRequestDto productRequestDto = new ProductRequestDto(name, price, desc, img);
            Product product = new Product(productRequestDto);
            products.add(product);
        }

        // Webdriver 종료
        driver.close();

        return products;

    }

}
