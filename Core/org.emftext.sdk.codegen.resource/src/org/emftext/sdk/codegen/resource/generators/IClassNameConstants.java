/*******************************************************************************
 * Copyright (c) 2006-2012
 * Software Technology Group, Dresden University of Technology
 * DevBoost GmbH, Berlin, Amtsgericht Charlottenburg, HRB 140026
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany;
 *   DevBoost GmbH - Berlin, Germany
 *      - initial API and implementation
 ******************************************************************************/
package org.emftext.sdk.codegen.resource.generators;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.event.DocumentListener;

import org.antlr.runtime3_4_0.ANTLRInputStream;
import org.antlr.runtime3_4_0.ANTLRStringStream;
import org.antlr.runtime3_4_0.BitSet;
import org.antlr.runtime3_4_0.CommonToken;
import org.antlr.runtime3_4_0.CommonTokenStream;
import org.antlr.runtime3_4_0.EarlyExitException;
import org.antlr.runtime3_4_0.FailedPredicateException;
import org.antlr.runtime3_4_0.IntStream;
import org.antlr.runtime3_4_0.Lexer;
import org.antlr.runtime3_4_0.MismatchedNotSetException;
import org.antlr.runtime3_4_0.MismatchedRangeException;
import org.antlr.runtime3_4_0.MismatchedSetException;
import org.antlr.runtime3_4_0.MismatchedTokenException;
import org.antlr.runtime3_4_0.MismatchedTreeNodeException;
import org.antlr.runtime3_4_0.NoViableAltException;
import org.antlr.runtime3_4_0.RecognitionException;
import org.antlr.runtime3_4_0.Token;
import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.ecore.templates.editor.Editor;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.impl.BasicEObjectImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.BasicInternalEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreValidator;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.validation.model.ConstraintStatus;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * Constants for class names used in the generated code.
 */
@SuppressWarnings("restriction")
public interface IClassNameConstants extends org.emftext.sdk.codegen.composites.IClassNameConstants {

	public static String ABSTRACT_PREFERENCE_INITIALIZER = AbstractPreferenceInitializer.class.getName();
	public static String ADAPTER = Adapter.class.getName();
	public static String ADAPTER_IMPL = AdapterImpl.class.getName();
	public static String ANTLR_INPUT_STREAM = ANTLRInputStream.class.getName();
	public static String ANTLR_PARSER = org.antlr.runtime3_4_0.Parser.class.getName();
	public static String ANTLR_STRING_STREAM = ANTLRStringStream.class.getName();
	public static String ARRAY = java.lang.reflect.Array.class.getName();
	public static String ARRAYS = java.util.Arrays.class.getName();
	public static String ASSERT = Assert.class.getName();
	public static String BASIC_COMMAND_STACK = BasicCommandStack.class.getName();
	public static String BASIC_E_LIST = BasicEList.class.getName();
	public static String BASIC_E_MAP = BasicEMap.class.getName();
	public static String BASIC_E_OBJECT_IMPL = BasicEObjectImpl.class.getName();
	public static String BASIC_INTERNAL_E_LIST = BasicInternalEList.class.getName();
	public static String BIT_SET = BitSet.class.getName();
	public static String BUFFERED_INPUT_STREAM = BufferedInputStream.class.getName();
	public static String BUFFERED_OUTPUT_STREAM = BufferedOutputStream.class.getName();
	public static String BUFFERED_READER = BufferedReader.class.getName();
	public static String BUNDLE = Bundle.class.getName();
	public static String BUNDLE_CONTEXT = BundleContext.class.getName();
	public static String BYTE_ARRAY_INPUT_STREAM = ByteArrayInputStream.class.getName();
	public static String BYTE_ARRAY_OUTPUT_STREAM = ByteArrayOutputStream.class.getName();
	public static String CHANGE_DESCRIPTION = ChangeDescription.class.getName();
	public static String CHANGE_RECORDER = ChangeRecorder.class.getName();
	public static String COLLECTION = Collection.class.getName();
	public static String COLLECTIONS = Collections.class.getName();
	public static String COMMON_TOKEN = CommonToken.class.getName();
	public static String COMMON_TOKEN_STREAM = CommonTokenStream.class.getName();
	public static String COMPARABLE = Comparable.class.getName();
	public static String COMPARATOR = Comparator.class.getName();
	public static String CONNECT_EXCEPTION = ConnectException.class.getName();
	public static String CONSTRAINT_STATUS = ConstraintStatus.class.getName();
	public static String CORE_EXCEPTION = CoreException.class.getName();
	public static String DIAGNOSTIC = org.eclipse.emf.common.util.Diagnostic.class.getName();
	public static String DIAGNOSTICIAN = org.eclipse.emf.ecore.util.Diagnostician.class.getName();
	public static String DOCUMENT_LISTENER = DocumentListener.class.getName();
	public static String EARLY_EXIT_EXCEPTION = EarlyExitException.class.getName();
	public static String ECORE_FACTORY = EcoreFactory.class.getName();
	public static String ECORE_PLUGIN = EcorePlugin.class.getName();
	public static String ECORE_UTIL = EcoreUtil.class.getName();
	public static String ECORE_VALIDATOR = EcoreValidator.class.getName();
	public static String EDITOR = Editor.class.getName();
	public static String ELEMENT_BASED_TEXT_DIAGNOSTIC = "ElementBasedTextDiagnostic";
	public static String EMF_MODEL_VALIDATION_PLUGIN = org.eclipse.emf.validation.internal.EMFModelValidationPlugin.class.getName();
	public static String EMPTY_STACK_EXCEPTION = EmptyStackException.class.getName();
	public static String ENUMERATOR = Enumerator.class.getName();
	public static String EVALUATION_MODE = EvaluationMode.class.getName();
	public static String E_ATTRIBUTE = EAttribute.class.getName();
	public static String E_CLASS = EClass.class.getName();
	public static String E_CLASSIFIER = EClassifier.class.getName();
	public static String E_DATA_TYPE = EDataType.class.getName();
	public static String E_ENUM = EEnum.class.getName();
	public static String E_ENUM_LITERAL = EEnumLiteral.class.getName();
	public static String E_FACTORY = EFactory.class.getName();
	public static String E_LIST = EList.class.getName();
	public static String E_MAP = EMap.class.getName();
	public static String E_OBJECT = EObject.class.getName();
	public static String E_OBJECT_IMPL = EObjectImpl.class.getName();
	public static String E_OPERATION = EOperation.class.getName();
	public static String E_PACKAGE = EPackage.class.getName();
	public static String E_REFERENCE = EReference.class.getName();
	public static String E_STRUCTURAL_FEATURE = EStructuralFeature.class.getName();
	public static String FAILED_PREDICATE_EXCEPTION = FailedPredicateException.class.getName();
	public static String FIELD = Field.class.getName();
	public static String FILE = File.class.getName();
	public static String FILE_INPUT_STREAM = FileInputStream.class.getName();
	public static String FILE_LOCATOR = FileLocator.class.getName();
	public static String FILE_NOT_FOUND_EXCEPTION = FileNotFoundException.class.getName();
	public static String FILE_OUTPUT_STREAM = FileOutputStream.class.getName();
	public static String GEN_CLASS = GenClass.class.getName();
	public static String GEN_FEATURE = GenFeature.class.getName();
	public static String GEN_PACKAGE = GenPackage.class.getName();
	public static String IDENTITY_HASH_MAP = IdentityHashMap.class.getName();
	public static String ILLEGAL_ACCESS_EXCEPTION = IllegalAccessException.class.getName();
	public static String ILLEGAL_ARGUMENT_EXCEPTION = IllegalArgumentException.class.getName();
	public static String INCREMENTAL_PROJECT_BUILDER = IncrementalProjectBuilder.class.getName();
	public static String INPUT_STREAM = InputStream.class.getName();
	public static String INPUT_STREAM_READER = InputStreamReader.class.getName();
	public static String INTERNAL_E_LIST = InternalEList.class.getName();
	public static String INTERNAL_E_OBJECT = InternalEObject.class.getName();
	public static String INT_STREAM = IntStream.class.getName();
	public static String INVOCATION_HANDLER = InvocationHandler.class.getName();
	public static String INVOCATION_TARGET_EXCEPTION = InvocationTargetException.class.getName();
	public static String IO_EXCEPTION = IOException.class.getName();
	public static String ITERATOR = Iterator.class.getName();
	public static String I_ADAPTABLE = org.eclipse.core.runtime.IAdaptable.class.getName();
	public static String I_ADAPTER_FACTORY = IAdapterFactory.class.getName();
	public static String I_BATCH_VALIDATOR = IBatchValidator.class.getName();
	public static String I_COMMAND = ICommand.class.getName();
	public static String I_CONFIGURATION_ELEMENT = IConfigurationElement.class.getName();
	public static String I_CONTAINER = IContainer.class.getName();
	public static String I_EXECUTABLE_EXTENSION = IExecutableExtension.class.getName();
	public static String I_EXTENSION_REGISTRY = IExtensionRegistry.class.getName();
	public static String I_FILE = IFile.class.getName();
	public static String I_MARKER = IMarker.class.getName();
	public static String I_MARKER_DELTA = IMarkerDelta.class.getName();
	public static String I_PATH = IPath.class.getName();
	public static String I_PROGRESS_MONITOR = IProgressMonitor.class.getName();
	public static String I_PROJECT = IProject.class.getName();
	public static String I_PROJECT_DESCRIPTION = IProjectDescription.class.getName();
	public static String I_PROJECT_NATURE = IProjectNature.class.getName();
	public static String I_RESOURCE = IResource.class.getName();
	public static String I_RESOURCE_CHANGE_EVENT = IResourceChangeEvent.class.getName();
	public static String I_RESOURCE_CHANGE_LISTENER = IResourceChangeListener.class.getName();
	public static String I_RESOURCE_DELTA = IResourceDelta.class.getName();
	public static String I_RESOURCE_DELTA_VISITOR = IResourceDeltaVisitor.class.getName();
	public static String I_RESOURCE_VISITOR = IResourceVisitor.class.getName();
	public static String I_STATUS = IStatus.class.getName();
	public static String I_WORKSPACE = IWorkspace.class.getName();
	public static String I_WORKSPACE_ROOT = IWorkspaceRoot.class.getName();
	public static String I_WORKSPACE_RUNNABLE = IWorkspaceRunnable.class.getName();
	public static String JOB = org.eclipse.core.runtime.jobs.Job.class.getName();
	public static String LEXER = Lexer.class.getName();
	public static String LINKED_HASH_MAP = LinkedHashMap.class.getName();
	public static String LINKED_HASH_SET = LinkedHashSet.class.getName();
	public static String LINKED_LIST = LinkedList.class.getName();
	public static String LISTENER_LIST = ListenerList.class.getName();
	public static String LIST_ITERATOR = ListIterator.class.getName();
	public static String MALFORMED_URL_EXCEPTION = MalformedURLException.class.getName();
	public static String MANY_INVERSE = EObjectWithInverseResolvingEList.ManyInverse.class.getCanonicalName();
	public static String MAP = Map.class.getName();
	public static String MAP_ENTRY = Map.Entry.class.getCanonicalName();
	public static String MATCHER = Matcher.class.getName();
	public static String MESSAGE_DIGEST = MessageDigest.class.getName();
	public static String METHOD = java.lang.reflect.Method.class.getName();
	public static String MISMATCHED_NOT_SET_EXCEPTION = MismatchedNotSetException.class.getName();
	public static String MISMATCHED_RANGE_EXCEPTION = MismatchedRangeException.class.getName();
	public static String MISMATCHED_SET_EXCEPTION = MismatchedSetException.class.getName();
	public static String MISMATCHED_TOKEN_EXCEPTION = MismatchedTokenException.class.getName();
	public static String MISMATCHED_TREE_NODE_EXCEPTION = MismatchedTreeNodeException.class.getName();
	public static String MODEL_VALIDATION_SERVICE = ModelValidationService.class.getName();
	public static String MODIFIER = java.lang.reflect.Modifier.class.getName();
	public static String NOTIFICATION = Notification.class.getName();
	public static String NOTIFICATION_CHAIN = NotificationChain.class.getName();
	public static String NOTIFIER = Notifier.class.getName();
	public static String NO_SUCH_ALGORITHM_EXCEPTION = NoSuchAlgorithmException.class.getName();
	public static String NO_SUCH_FIELD_EXCEPTION = NoSuchFieldException.class.getName();
	public static String NO_VIABLE_ALT_EXCEPTION = NoViableAltException.class.getName();
	public static String NULL_POINTER_EXCEPTION = NullPointerException.class.getName();
	public static String OUTPUT_STREAM = OutputStream.class.getName();
	public static String OUTPUT_STREAM_WRITER = OutputStreamWriter.class.getName();
	public static String PATH = Path.class.getName();
	public static String PATTERN = java.util.regex.Pattern.class.getName();
	public static String PLATFORM = Platform.class.getName();
	public static String PLUGIN = Plugin.class.getName();
	public static String POSITION_BASED_TEXT_DIAGNOSTIC = "PositionBasedTextDiagnostic";
	public static String PRINTER_WRITER = PrintWriter.class.getName();
	public static String PRINT_STREAM = PrintStream.class.getName();
	public static String PROPERTY_TESTER = PropertyTester.class.getName();
	public static String PROXY = Proxy.class.getName();
	public static String PUSHBACK_READER = PushbackReader.class.getName();
	public static String READER = Reader.class.getName();
	public static String RECOGNITION_EXCEPTION = RecognitionException.class.getName();
	public static String RECOGNIZER_SHARED_STATE = org.antlr.runtime3_4_0.RecognizerSharedState.class.getName();
	public static String RESOLVER_SWITCH_FIELD_NAME = "resolverSwitch";
	public static String RESOURCE = org.eclipse.emf.ecore.resource.Resource.class.getName();
	public static String RESOURCES_PLUGIN = ResourcesPlugin.class.getName();
	public static String RESOURCE_BUNDLE = ResourceBundle.class.getName();
	public static String RESOURCE_DIAGNOSTIC = Diagnostic.class.getCanonicalName();
	public static String RESOURCE_FACTORY = Resource.Factory.class.getCanonicalName();
	public static String RESOURCE_IMPL = ResourceImpl.class.getName();
	public static String RESOURCE_SET = ResourceSet.class.getName();
	public static String RESOURCE_SET_IMPL = ResourceSetImpl.class.getName();
	public static String RUNTIME_EXCEPTION = RuntimeException.class.getName();
	public static String SAFE_RUNNER = SafeRunner.class.getName();
	public static String SECURITY_EXCEPTION = SecurityException.class.getName();
	public static String SERVER_SOCKET = ServerSocket.class.getName();
	public static String SET = Set.class.getName();
	public static String SETTING = Setting.class.getCanonicalName();
	public static String SOCKET = Socket.class.getName();
	public static String STACK = Stack.class.getName();
	public static String STATUS = Status.class.getName();
	public static String STRING_READER = StringReader.class.getName();
	public static String STRING_WRITER = StringWriter.class.getName();
	public static String TIMER = Timer.class.getName();
	public static String TIMER_TASK = TimerTask.class.getName();
	public static String TOKEN = Token.class.getName();
	public static String TOKEN_STREAM = org.antlr.runtime3_4_0.TokenStream.class.getName();
	public static String TREE_MAP = TreeMap.class.getName();
	public static String UNKNOWN_HOST_EXCEPTION = UnknownHostException.class.getName();
	public static String URI = org.eclipse.emf.common.util.URI.class.getName();
	public static String URI_CONVERTER = URIConverter.class.getName();
	public static String URL = URL.class.getName();
	public static String ZIP_ENTRY = ZipEntry.class.getName();
	public static String ZIP_FILE = ZipFile.class.getName();
	
	public static String I_SCHEDULING_RULE = ISchedulingRule.class.getName();

	// for the classes contained in the org.eclipse.debug plug-in, we use string 
	// constants instead of referencing the class objects, because this cause
	// trouble in some environments (e.g., PDE build).
	//
	// this fixes bug 1815: Replace class name constants from DebugUI plug-in with static strings
	public static String ABSTRACT_SOURCE_LOOKUP_DIRECTOR = "org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector";
	public static String ABSTRACT_SOURCE_LOOKUP_PARTICIPANT = "org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant";
	public static String DEBUG_ELEMENT = "org.eclipse.debug.core.model.DebugElement";
	public static String DEBUG_EVENT = "org.eclipse.debug.core.DebugEvent";
	public static String DEBUG_EXCEPTION = "org.eclipse.debug.core.DebugException";
	public static String DEBUG_PLUGIN = "org.eclipse.debug.core.DebugPlugin";
	public static String I_BREAKPOINT = "org.eclipse.debug.core.model.IBreakpoint";
	public static String I_BREAKPOINT_MANAGER = "org.eclipse.debug.core.IBreakpointManager";
	public static String I_DEBUG_TARGET = "org.eclipse.debug.core.model.IDebugTarget";
	public static String I_LAUNCH = "org.eclipse.debug.core.ILaunch";
	public static String I_LAUNCH_CONFIGURATION = "org.eclipse.debug.core.ILaunchConfiguration";
	public static String I_LAUNCH_MANAGER = "org.eclipse.debug.core.ILaunchManager";
	public static String I_MEMORY_BLOCK = "org.eclipse.debug.core.model.IMemoryBlock";
	public static String I_PROCESS = "org.eclipse.debug.core.model.IProcess";
	public static String I_REGISTER_GROUP = "org.eclipse.debug.core.model.IRegisterGroup";
	public static String I_SOURCE_CONTAINER = "org.eclipse.debug.core.sourcelookup.ISourceContainer";
	public static String I_SOURCE_CONTAINER_TYPE = "org.eclipse.debug.core.sourcelookup.ISourceContainerType";
	public static String I_SOURCE_LOOKUP_DIRECTOR = "org.eclipse.debug.core.sourcelookup.ISourceLookupDirector";
	public static String I_SOURCE_LOOKUP_PARTICIPANT = "org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant";
	public static String I_SOURCE_PATH_COMPUTER_DELEGATE = "org.eclipse.debug.core.sourcelookup.ISourcePathComputerDelegate";
	public static String I_STACK_FRAME = "org.eclipse.debug.core.model.IStackFrame";
	public static String I_STREAMS_PROXY = "org.eclipse.debug.core.model.IStreamsProxy";
	public static String I_THREAD = "org.eclipse.debug.core.model.IThread";
	public static String I_VALUE = "org.eclipse.debug.core.model.IValue";
	public static String I_VARIABLE = "org.eclipse.debug.core.model.IVariable";
	public static String LAUNCH_CONFIGURATION_DELEGATE = "org.eclipse.debug.core.model.LaunchConfigurationDelegate";
	public static String LINE_BREAKPOINT = "org.eclipse.debug.core.model.LineBreakpoint";
}
