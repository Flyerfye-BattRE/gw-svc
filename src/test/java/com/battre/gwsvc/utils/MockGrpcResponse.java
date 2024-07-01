package com.battre.gwsvc.utils;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.UnknownFieldSet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

class MockGrpcResponse implements Message {
  private final boolean success;

  public MockGrpcResponse(boolean success) {
    this.success = success;
  }

  public boolean getSuccess() {
    return success;
  }

  @Override
  public Parser<? extends Message> getParserForType() {
    return null;
  }

  @Override
  public ByteString toByteString() {
    return null;
  }

  @Override
  public byte[] toByteArray() {
    return new byte[0];
  }

  @Override
  public void writeTo(OutputStream output) throws IOException {}

  @Override
  public void writeDelimitedTo(OutputStream output) throws IOException {}

  @Override
  public boolean equals(Object other) {
    return false;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public String toString() {
    return null;
  }

  @Override
  public Builder newBuilderForType() {
    return null;
  }

  @Override
  public Builder toBuilder() {
    return null;
  }

  @Override
  public int getSerializedSize() {
    return 0;
  }

  @Override
  public void writeTo(CodedOutputStream output) {}

  @Override
  public boolean isInitialized() {
    return false;
  }

  @Override
  public Message getDefaultInstanceForType() {
    return null;
  }

  @Override
  public List<String> findInitializationErrors() {
    return null;
  }

  @Override
  public String getInitializationErrorString() {
    return null;
  }

  @Override
  public Descriptors.Descriptor getDescriptorForType() {
    return null;
  }

  @Override
  public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
    return null;
  }

  @Override
  public boolean hasOneof(Descriptors.OneofDescriptor oneof) {
    return false;
  }

  @Override
  public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneof) {
    return null;
  }

  @Override
  public boolean hasField(Descriptors.FieldDescriptor field) {
    return false;
  }

  @Override
  public Object getField(Descriptors.FieldDescriptor field) {
    return null;
  }

  @Override
  public int getRepeatedFieldCount(Descriptors.FieldDescriptor field) {
    return 0;
  }

  @Override
  public Object getRepeatedField(Descriptors.FieldDescriptor field, int index) {
    return null;
  }

  @Override
  public UnknownFieldSet getUnknownFields() {
    return null;
  }
}
