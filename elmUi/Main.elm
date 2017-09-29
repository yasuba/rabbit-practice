module Main exposing (..)

import Html exposing (..)
import Model exposing (Model, initialModel)
import Msg exposing (Msg)
import Update.Main exposing (update)
import View.Main exposing (view)
import Update.Http exposing (fetchData)

init : (Model, Cmd Msg)
init =
    initialModel ! [ fetchData ]

-- MAIN

main : Program Never Model Msg
main =
    program
        { init = init
        , view = view
        , update = update
        , subscriptions = \_ -> Sub.none
        }
