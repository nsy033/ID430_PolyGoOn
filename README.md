# Poly Go On

✔️ 폴리곤아트란 다각형을 이어 붙여 어떤 이미지를 3D 형태로 보이도록 만드는 디자인기법을 의미한다. 폴리곤아트가 초심자도 가볍게 시작하여 꽤 높은 퀄리티의 결과물을 얻을 수있다고 알려진 기법이기는 하나, 제대로된 결과를 내기 위해서는 생각보다 많은 시간을 들여야하기도 하고 고려해야할 부분들도 많다.

✔️ 우리는 초심자들이 보다 쉽고 빠르게 폴리곤아트를 제작할 수있도록 돕는 방법을 생각해보았다. 그 결과, 사용자가 폴리곤아트를 완성해나가는 전 과정에서, 간단한 조작만으로 삼각형을 생성하고, 쉽게 범할 수 있는 실수를 충분히 방지하며, 원하는 레퍼런스 이미지의 분위기를 보다 잘 모방할 수 있도록 돕는 인터랙티브한 어플리케이션을 개발하게 되었다.

# Available Feautures

- 작업에 참고할 배경 레퍼런스 이미지를 드래그 앤 드롭을 통해 로드
- 오직 세 번의 클릭탭만으로 하나의 올바른 polygon을 생성
- 원하지 않는 polygon이 존재할 경우 해당 polygon을 삭제
- 생성된 polygon을 비슷한 위치에서 조금만 수정하고 싶을 경우 polygon을 변형
- polygon을 더 작은 조각으로 나누어 면적을 보다 다양하게 활용
- 현재까지의 작업을 확인하고 싶을 경우 배경 이미지를 숨기고 현재까지 작업한 폴리곤아트만을 확인
- 현재까지의 작업을 export하여 임시 저장
- 임시 저장한 작업물을 import
- 전체 폴리곤 아트의 HSB 색상을 변경
- 완성된 폴리곤 아트를 이미지 파일로 저장

# Operations

To start polygon art work, drag reference image to background and press enter.

## Mouse Events
| Action | Description |
|:---:|:---:|
| tap | add new vertex for polygon |
| drag | move new vertex |
| long press polygon | select polygon <br> delete selected polygon if it is dragged to conner |
| double tap / ctrl + tap | split existing polygon |

## Key Bindings
| Key | Description |
|:---:|:---:|
| c | enter color adjust mode |
| v | hide / show image |
| shift | enter deform mode <br> drag vertex - move existing vertex |
| shift + ctrl | enter separate mode <br> drag vertex - separate vertex from merged vertices |
| ctrl + s → enter | save as png image |
| ctrl + e → enter | export as json file |

# Demo Video

<a href="https://www.youtube.com/watch?v=CjVw0tOz9x4">
  <img src=https://img.youtube.com/vi/CjVw0tOz9x4/maxresdefault.jpg  width="480" height="320"/>
</a>

https://youtu.be/CjVw0tOz9x4

