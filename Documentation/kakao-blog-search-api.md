카카오 블로그 검색 API 사용하기
======
## 카카오 블로그 검색 API 요청 양식
| Name    | Type     | Description                                                     | Default    | Required |
|---------|----------|-----------------------------------------------------------------|------------|----------|
| `query` | String   | 검색을 원하는 키워드. 특정 블로그 글만 검색하고 싶다면 블로그 URI 를 포함.    | -          | O        |
| `sort`  | String   | 결과문서 정렬 방식. `accuracy`(정확도)/`recency`(최신)                | `accuracy` | X        |
| `page`  | Integer  | 결과 페이지 번호. 1~50 사이의 값.                                    | 1          | X        |
| `size`  | Integer  | 한 페이지에 보여질 문서 수. 1~50 사이의 값.                            | 10         | X        |

## 카카오 블로그 검색 API 응답 양식
### meta
| Name           | Type    | Description                                                                          |
|----------------|---------|--------------------------------------------------------------------------------------|
| total_count    | Integer | 검색된 문서 수                                                                          |
| pageable_count | Integer | 처음부터 요청한 페이지까지 노출 가능한 문서 수 (중복된 문서 제외)                                 |
| is_end         | Boolean | 현재 페이지가 마지막 페이지인지 여부. 값이 `false` 이면 page 를 증가시켜 다음 페이지를 요청할 수 있음  |

### documents
| Name       | Type      | Description                                                       |
|------------|-----------|-------------------------------------------------------------------|
| title      | String    | 블로그 글 제목                                                       |
| contents   | String    | 블로그 글 요약                                                       |
| url        | String    | 블로그 글 URI                                                       |
| blogname   | String    | 블로그 이름                                                         |
| thumbnail  | String    | 미리보기 이미지 URI                                                  |
| datetime   | Datetime  | 블로그 글 작성시간, ISO8601. [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz] |


### 카카오 블로그 검색 API 사용 예시 1
REST API Key 발급받자.   
이후 일단 shell variable 로 넣어놓기 (추후 암호화 필요)
``` shell
KAKAO_REST_API_KEY=429f206db1aa4df9c85cae6171fc5423
```

카카오 블로그 검색 API는 요청으로 www form 형식을 받는다. (`Content-type: application/x-www-form-urlencode`)      
https://brunch.co.kr/@tourism 이라는 블로그 URI 를 검색해보자.

```shell
curl -v -X GET \
"https://dapi.kakao.com/v2/search/blog" \
--data-urlencode "query=https://brunch.co.kr/@tourism" \
-H "Content-Type: application/x-www-form-urlencoded" \
-H "Authorization: KakaoAK ${KAKAO_REST_API_KEY}" \
-H "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7"
```

아래와 같이 답변 body가 채워져 온다:
```json
{
  "documents": [
    {
      "blogname": "\u003c개념여행\u003e, \u003c여행을가다, 희망을보다\u003e 저자 정란수",
      "contents": "브런치 메인에도 선정이 되었습니다. 정란수의 브런치 연재글은 다음의 링크에서 보실 수 있습니다~ 브런치 내 제 글을 보시려면 다음의 링크를 따라가주세요 ^^ \u003cb\u003ehttps://brunch.co.kr/@tourism\u003c/b\u003e 정란수의 브런치 여행다니면서 일하는 \u0026#34;한량\u0026#34;! \u0026lt;개념여행\u0026gt; 저자이면서, 관광개발 컨설팅을 하고 돌아다님 www.tourism.re.kr...",
      "datetime": "2016-01-17T23:24:00.000+09:00",
      "thumbnail": "https://search3.kakaocdn.net/argon/130x130_85_c/7dtORyQIlj3",
      "title": "[여행?희망!] _ 브런치 연재글 다음 메인에 선정",
      "url": "https://blog.naver.com/jeongransoo/220600431408"
    },
    {
      "blogname": "\u003c개념여행\u003e, \u003c여행을가다, 희망을보다\u003e 저자 정란수",
      "contents": "희망을 발견하자는 내용으로, 이른바 ...",
      "datetime": "2015-10-17T16:44:00.000+09:00",
      "thumbnail": "https://search4.kakaocdn.net/argon/130x130_85_c/EvscfrxMzLn",
      "title":"여행을 통해 희망을 발견하기 브런치 연재 시작",
      "url":"https://blog.naver.com/jeongransoo/220511478588"
    }
  ],
  "meta": {
    "is_end": true,
    "pageable_count": 2,
    "total_count": 2
  }
}
```

### 카카오 블로그 검색 API 사용 예시 2
키워드, sort, page, size 이용하기

```shell
curl -v -X GET "https://dapi.kakao.com/v2/search/blog" \
--data-urlencode "query=집짓기" \
--data "sort=recency" \
--data "page=2" \
--data "size=5" \
-H "Content-Type: application/x-www-form-urlencoded" \
-H "Authorization: KakaoAK ${KAKAO_REST_API_KEY}"
```