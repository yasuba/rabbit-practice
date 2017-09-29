module Update.Main exposing (..)

import Update.Actions exposing (..)
import Update.Http exposing (..)
import Model exposing (..)
import Msg exposing (..)

update : Msg -> Model -> (Model, Cmd Msg)
update msg model =
  case msg of
    YospaceAdReplacementEnabled boolean ->
      ( yospaceAdReplacementEnabled boolean model )
    Channel name ->
      ( channel name model )
    TokenRequired boolean ->
      ( tokenRequired boolean model )
    NewProduct productName ->
      ( newProduct productName model )
    Update ->
      ( model, submitChoices model.newAdreplacementEntry )
    UpdateChoices (Ok _) ->
      ( updateAdreplacementSettings model, Cmd.none )
    UpdateChoices (Err err) ->
      ( model, Cmd.none )
    Fetch (Ok entry) ->
      ( fetchAdreplacementSettings model entry, Cmd.none )
    Fetch (Err _) ->
      ( model, Cmd.none )
