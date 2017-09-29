module View.Main exposing (..)

import Html exposing (..)
import Html.Events exposing (onClick)
import Html.Attributes exposing (..)
import Model exposing (..)
import Msg exposing (..)
import Select
import Bootstrap.Grid as Grid
import Bootstrap.Grid.Col as Col
import Bootstrap.Grid.Row as Row
import Bootstrap.Button as Button

view : Model -> Html Msg
view model =
    div []
      [ form model
      , viewData "Current state" model.oldAdreplacementEntry
      , dbButtons model
      ]


col : Col.Option msg -> Html msg -> Grid.Column msg
col colSize function =
  Grid.col [ colSize] [ div [] [ function ] ]


row : String -> List (Grid.Column msg) -> Html msg
row className cols =
  Grid.row [ Row.attrs [ class className] ] cols


selectChannel : List String -> List (Html Msg)
selectChannel channels =
  List.map (\x -> checkbox x (Channel x)) channels


form : Model -> Html Msg
form model =
  Grid.container []
    [ h1 [] [ text "Update AdReplacement Settings"]
    , row "selectSettings"
      [ col Col.xs4 (text("Product is"))
      , col Col.xs6 (Select.fromSelected [ "ios" ] NewProduct "ios")
      ]
    , row "selectSettings"
      [ col Col.xs4 (text "Is AdReplacement enabled?")
      , col Col.xs6 (fieldset []
          [ radio "yospace" "True" (YospaceAdReplacementEnabled True)
          , radio "yospace" "False" (YospaceAdReplacementEnabled False)
          ])
      ]
    , row "selectSettings"
      [ col Col.xs4 (text ("What are the AdReplacement-enabled channels?"))
      , col Col.xs6 (fieldset [] (selectChannel ["itv1", "itv2", "itv3", "itv4", "itvbe", "citv"]))
      ]
    , row "selectSettings"
      [ col Col.xs4 (text ("Is Token Required?") )
      , col Col.xs6 (fieldset []
            [ radio "akamai" "True" (TokenRequired True)
            , radio "akamai" "False" (TokenRequired False)
            ])
      ]
    ]


viewData : String -> AdReplacementEntry -> Html Msg
viewData title entry =
    div [ class "col-md-4 two-column-table" ]
        [ table [ class "table table-striped" ]
            [ thead []
                [ tr []
                    [ th [] [ text title ]
                    , th [] []
                    ]
                ]
            , tbody []
                [ tr []
                    [ td [] [ text "Product" ]
                    , td [] [ text (toString entry.productName) ]
                    ]
                , tr []
                    [ td [] [ text "Is AdReplacement enabled?" ]
                    , td [] [ text (toString entry.isAdReplacementEnabled) ]
                    ]
                , tr []
                    [ td [] [ text "AdReplacement Channels" ]
                    , td [] [ text (toString entry.adReplacementChannels) ]
                    ]
                , tr []
                    [ td [] [ text "Is Token Required?" ]
                    , td [] [ text (toString entry.isTokenRequired) ]
                    ]
                ]
            ]
        ]


dbButtons : Model -> Html Msg
dbButtons model =
  Grid.container []
    [ Grid.row []
      [ Grid.col [ Col.xs2 ]
        [ div [ style [ ("margin-top", "40px") ]] [
          Button.linkButton
            [ Button.primary, Button.attrs [ onClick Update ] ]
            [text "Update"]
          ]
        ]
      ]
    ]


radio : String -> String -> msg -> Html msg
radio groupName value msg =
  label []
    [ input [ type_ "radio", name groupName, onClick msg ] []
    , text value
    ]


checkbox : String -> msg -> Html msg
checkbox name msg =
  label
    [ style [("margin-right", "20px")]
    ]
    [ input [ type_ "checkbox", onClick msg ] []
    , text name
    ]
