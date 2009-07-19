package org.emftext.sdk.codegen.generators;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedNotSetException;
import org.antlr.runtime.MismatchedRangeException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.MismatchedTreeNodeException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.runtime.EMFTextRuntimePlugin;
import org.emftext.runtime.IOptionProvider;
import org.emftext.runtime.IOptions;
import org.emftext.runtime.IResourcePostProcessor;
import org.emftext.runtime.resource.EProblemType;
import org.emftext.runtime.resource.IContextDependentURIFragment;
import org.emftext.runtime.resource.IElementMapping;
import org.emftext.runtime.resource.IExpectedElement;
import org.emftext.runtime.resource.ILocationMap;
import org.emftext.runtime.resource.IProblem;
import org.emftext.runtime.resource.IReferenceMapping;
import org.emftext.runtime.resource.IReferenceResolveResult;
import org.emftext.runtime.resource.IReferenceResolver;
import org.emftext.runtime.resource.IReferenceResolverSwitch;
import org.emftext.runtime.resource.ITextDiagnostic;
import org.emftext.runtime.resource.ITextScanner;
import org.emftext.runtime.resource.ITextParser;
import org.emftext.runtime.resource.ITextResource;
import org.emftext.runtime.resource.ITextToken;
import org.emftext.runtime.resource.ITokenResolveResult;
import org.emftext.runtime.resource.ITokenResolver;
import org.emftext.runtime.resource.ITokenResolverFactory;
import org.emftext.runtime.resource.ITokenStyle;
import org.emftext.runtime.resource.IURIMapping;
import org.emftext.runtime.resource.impl.AbstractEMFTextScanner;
import org.emftext.runtime.resource.impl.AbstractEMFTextParser;
import org.emftext.runtime.resource.impl.AbstractProblem;
import org.emftext.runtime.resource.impl.AbstractTextResourcePluginMetaInformation;
import org.emftext.runtime.resource.impl.DummyEObject;
import org.emftext.runtime.resource.impl.LocationMap;
import org.emftext.runtime.resource.impl.TokenResolveResult;
import org.emftext.runtime.ui.AntlrTextLexer;
import org.emftext.runtime.util.CastUtil;
import org.emftext.runtime.util.EObjectUtil;
import org.emftext.runtime.util.ListUtil;
import org.emftext.runtime.util.MapUtil;
import org.emftext.runtime.util.StringUtil;

/**
 * Constants for class names used in the generated code.
 * 
 * TODO find all other occurrences of class name in the generator package
 * and use constants instead. 
 */
public interface IClassNameConstants {

	public String I_TEXT_LEXER = ITextScanner.class.getName();
	public String I_TEXT_TOKEN = ITextToken.class.getName();
	public String BYTE_ARRAY_INPUT_STREAM = ByteArrayInputStream.class.getName();
	public String ANTLR_TEXT_LEXER = AntlrTextLexer.class.getName();
	public String COMPARATOR = Comparator.class.getName();
	public String I_REFERENCE_RESOLVER = IReferenceResolver.class.getName();
	public String ABSTRACT_EMF_TEXT_PARSER = AbstractEMFTextParser.class.getName();
	public String ABSTRACT_EMF_TEXT_SCANNER = AbstractEMFTextScanner.class.getName();
	public String MATCHER = Matcher.class.getName();
	public String PATTERN = java.util.regex.Pattern.class.getName();
	public String INPUT_STREAM_READER = java.io.InputStreamReader.class.getName();
	
	public String ABSTRACT_PROBLEM = AbstractProblem.class.getName();
	public String ABSTRACT_TEXT_RESOURCE_PLUGIN_META_INFORMATION = AbstractTextResourcePluginMetaInformation.class.getName();
	public String ARRAYS = java.util.Arrays.class.getName();
	public String ARRAY_LIST = ArrayList.class.getName();
	public String BASIC_E_LIST = BasicEList.class.getName();
	public String BIT_SET = BitSet.class.getName();
	public String BUFFERED_OUTPUT_STREAM = BufferedOutputStream.class.getName();
	public String CAST_UTIL = CastUtil.class.getName();
	public String COLLECTION = Collection.class.getName();
	public String COLLECTIONS = Collections.class.getName();
	public String COMMON_TOKEN = CommonToken.class.getName();
	public String CORE_EXCEPTION = CoreException.class.getName();
	public String DIAGNOSTIC = Diagnostic.class.getName().replace("$", ".");
	public String DUMMY_E_OBJECT = DummyEObject.class.getName();
	public String EARLY_EXIT_EXCEPTION = EarlyExitException.class.getName();
	public String ECORE_UTIL = EcoreUtil.class.getName();
	public String ELEMENT_BASED_TEXT_DIAGNOSTIC = "ElementBasedTextDiagnostic";
	public String EMFTEXT_RUNTIME_PLUGIN = EMFTextRuntimePlugin.class.getName();
	public String EXCEPTION = Exception.class.getName();
	public String E_ATTRIBUTE = EAttribute.class.getName();
	public String E_CLASS = EClass.class.getName();
	public String E_MAP = EMap.class.getName();
	public String E_OBJECT = EObject.class.getName();
	public String E_OBJECT_UTIL = EObjectUtil.class.getName();
	public String E_OPERATION = EOperation.class.getName();
	public String E_PROBLEM_TYPE = EProblemType.class.getName();
	public String E_REFERENCE = EReference.class.getName();
	public String E_STRUCTURAL_FEATURE = EStructuralFeature.class.getName();
	public String FAILED_PREDICATE_EXCEPTION = FailedPredicateException.class.getName();
	public String HASH_MAP = HashMap.class.getName();
	public String ILLEGAL_ARGUMENT_EXCEPTION = IllegalArgumentException.class.getName();
	public String INPUT_STREAM = InputStream.class.getName();
	public String INTEGER = Integer.class.getName();
	public String INTERNAL_E_OBJECT = InternalEObject.class.getName();
	public String INT_STREAM = IntStream.class.getName();
	public String IO_EXCEPTION = IOException.class.getName();
	public String ITERATOR = Iterator.class.getName();
	public String I_CONFIGURATION_ELEMENT = IConfigurationElement.class.getName();
	public String I_CONTEXT_DEPENDANT_URI_FRAGMENT_FACTORY = org.emftext.runtime.resource.IContextDependentURIFragmentFactory.class.getName();
	public String I_CONTEXT_DEPENDENT_URI_FRAGMENT = IContextDependentURIFragment.class.getName();
	public String I_ELEMENT_MAPPING = IElementMapping.class.getName();
	public String I_EXPECTED_ELEMENT = IExpectedElement.class.getName();
	public String I_EXTENSION_REGISTRY = IExtensionRegistry.class.getName();
	public String I_LOCATION_MAP = ILocationMap.class.getName();
	public String I_OPTIONS = IOptions.class.getName();
	public String I_OPTION_PROVIDER = IOptionProvider.class.getName();
	public String I_PROBLEM = IProblem.class.getName();
	public String I_REFERENCE_MAPPING = IReferenceMapping.class.getName();
	public String I_REFERENCE_RESOLVER_SWITCH = IReferenceResolverSwitch.class.getName();
	public String I_REFERENCE_RESOLVE_RESULT = IReferenceResolveResult.class.getName();
	public String I_RESOURCE_POST_PROCESSOR = IResourcePostProcessor.class.getName();
	public String I_RESOURCE_POST_PROCESSOR_PROVIDER = org.emftext.runtime.IResourcePostProcessorProvider.class.getName();
	public String I_TEXT_DIAGNOSTIC = ITextDiagnostic.class.getName();
	public String I_TEXT_PARSER = ITextParser.class.getName();
	public String I_TEXT_RESOURCE = ITextResource.class.getName();
	public String I_TOKEN_RESOLVER = ITokenResolver.class.getName();
	public String I_TOKEN_RESOLVER_FACTORY = ITokenResolverFactory.class.getName();
	public String I_TOKEN_RESOLVE_RESULT = ITokenResolveResult.class.getName();
	public String TOKEN_RESOLVE_RESULT = TokenResolveResult.class.getName();
	public String I_TOKEN_STYLE = ITokenStyle.class.getName();
	public String I_URI_MAPPING = IURIMapping.class.getName();
	public String LEXER = Lexer.class.getName();
	public String LIST = List.class.getName();
	public String LINKED_LIST = LinkedList.class.getName();
	public String LIST_ITERATOR = ListIterator.class.getName();
	public String LIST_UTIL = ListUtil.class.getName();
	public String LOCATION_MAP = LocationMap.class.getName();
	public String MANY_INVERSE = EObjectWithInverseResolvingEList.ManyInverse.class.getName().replace('$', '.');
	public String MAP = Map.class.getName();
	public String MAP_UTIL = MapUtil.class.getName();
	public String MATH = Math.class.getName();
	public String MISMATCHED_NOT_SET_EXCEPTION = MismatchedNotSetException.class.getName();
	public String MISMATCHED_RANGE_EXCEPTION = MismatchedRangeException.class.getName();
	public String MISMATCHED_SET_EXCEPTION = MismatchedSetException.class.getName();
	public String MISMATCHED_TOKEN_EXCEPTION = MismatchedTokenException.class.getName();
	public String MISMATCHED_TREE_NODE_EXCEPTION = MismatchedTreeNodeException.class.getName();
	public String NO_VIABLE_ALT_EXCEPTION = NoViableAltException.class.getName();
	public String NULL_POINTER_EXCEPTION = NullPointerException.class.getName();
	public String OBJECT = Object.class.getName();
	public String OUTPUT_STREAM = OutputStream.class.getName();
	public String PLATFORM = Platform.class.getName();
	public String POSITION_BASED_TEXT_DIAGNOSTIC = "PositionBasedTextDiagnostic";
	public String PRINTER_WRITER = PrintWriter.class.getName();
	public String RECOGNITION_EXCEPTION = RecognitionException.class.getName();
	public String RESOLVER_SWITCH_FIELD_NAME = "resolverSwitch";
	public String RUNTIME_EXCEPTION = RuntimeException.class.getName();
	public String STACK = Stack.class.getName();
	public String STRING = String.class.getName();
	public String STRING_UTIL = StringUtil.class.getName();
	public String STRING_WRITER = StringWriter.class.getName();
	public String URI = org.eclipse.emf.common.util.URI.class.getName();
}
